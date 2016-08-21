package org.slinkyframework.environment.builder.couchbase.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ExecState;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.Image;
import com.spotify.docker.client.messages.PortBinding;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderException;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Creates and starts a Couchbase Docker container and the defined buckets within.
 * It then kills and removes the container when environment torn down.
 *
 * NOTE: Assumes the standard Docker Machine environment variables are set:
 *         DOCKER_TLS_VERIFY, DOCKER_HOST, DOCKER_CERT_PATH and DOCKER_MACHINE_NAME
 * Make sure these are set before running IDE or Maven build.
 */
public class DockerCouchbaseEnvironmentBuilder implements EnvironmentBuilder<CouchbaseBuildDefinition> {

    public static final String CONTAINER_NAME = "slinky_couchbase";
    public static final String COUCHBASE_LATEST_IMAGE_NAME = "couchbase:latest";

    private static final int ONE_SECOND = 1000;
    private static final int THIRTY_SECONDS = 30000;
    private static final long POLL_INTERVAL = ONE_SECOND;


    private static final Logger LOG = LoggerFactory.getLogger(DockerCouchbaseEnvironmentBuilder.class);

    private LocalCouchbaseEnvironmentBuilder localCouchbaseEnvironmentBuilder;

    public DockerCouchbaseEnvironmentBuilder(LocalCouchbaseEnvironmentBuilder localCouchbaseEnvironmentBuilder) {
        this.localCouchbaseEnvironmentBuilder = localCouchbaseEnvironmentBuilder;
    }

    @Override
    public void setUp(Set<CouchbaseBuildDefinition> buildDefinitions) {
        setUpDocker();
        localCouchbaseEnvironmentBuilder.setUp(buildDefinitions);
    }

    @Override
    public void tearDown(Set<CouchbaseBuildDefinition> buildDefinitions) {
        LOG.info("Tearing down Couchbase Docker container '{}'", CONTAINER_NAME);

        DockerClient docker = connectToDocker();

        Optional<Container> existingContainer = findExistingContainer(docker, CONTAINER_NAME);

        String containerId;

        if (existingContainer.isPresent()) {
            killAndRemoveContainer(docker, existingContainer.get());
            LOG.info("Couchbase docker container '{}' killed and removed", CONTAINER_NAME);
        } else {
            LOG.warn("Container '{}' was not running", CONTAINER_NAME);
        }
    }

    @Override
    public void cleanUp() {
        localCouchbaseEnvironmentBuilder.cleanUp();
    }

    private void pullContainer(DockerClient docker) {
        try {
            if (!findImage(docker, COUCHBASE_LATEST_IMAGE_NAME).isPresent()) {
                docker.pull(COUCHBASE_LATEST_IMAGE_NAME);
            }
        } catch (DockerException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to pull container: " + COUCHBASE_LATEST_IMAGE_NAME, e);
        }
    }

    private void killAndRemoveContainer(DockerClient docker, Container container) {
        try {
            if (container.status().startsWith("Up")) {
                LOG.debug("Killing Couchbase Docker container");
                docker.killContainer(container.id());
            }
            LOG.debug("Removing Couchbase Docker container");
            docker.removeContainer(container.id());
        } catch (DockerException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to kill and remove a container", e);
        }
    }

    private void setUpDocker() {
        LOG.info("Setting up Couchbase Docker container '{}'", CONTAINER_NAME);

        DockerClient docker = connectToDocker();

        pullContainer(docker);

        Optional<Container> existingContainer = findExistingContainer(docker, CONTAINER_NAME);

        String containerId;

        if (existingContainer.isPresent()) {
            LOG.warn("Container '{}' already exists", CONTAINER_NAME);
            killAndRemoveContainer(docker, existingContainer.get());
        }
        ContainerCreation container = createContainer(docker);
        containerId = container.id();

        startContainer(docker, containerId);
        waitForContainerToStart(docker, containerId);

        waitForCreateCouchbaseCluster(docker, containerId);
        initialiseCouchbaseCluster(docker, containerId);
    }

    private void waitForContainerToStart(DockerClient docker, String containerId) {
        boolean started = false;
        String targetHost = localCouchbaseEnvironmentBuilder.getHosts()[0];

        StopWatch sw = new StopWatch();
        sw.start();

        while (sw.getTime() < THIRTY_SECONDS) {
            if (portInUse(targetHost, 8091)) {
                started = true;
                break;
            } else {
                LOG.debug("{}:{} not yet available after {}, Sleep for {} ms", targetHost, 8091, sw.toString(), POLL_INTERVAL);
                sleepFor(POLL_INTERVAL);
            }
        }
        sw.stop();

        if (!started) {
            throw new EnvironmentBuilderException("Couchbase container has failed to start after " + sw.toString());
        }
    }

    private boolean portInUse(String host, int port) {
        LOG.debug("Check whether {}:{} is in use", host, port);
        Socket s = null;
        try {
            s = new Socket(host, port);
            LOG.debug("{}:{} is in use", host, port);
            return true;
        } catch (IOException e) {
            LOG.debug("{}:{} is not in use", host, port);
            return false;
        } finally {
            IOUtils.closeQuietly(s);
        }
    }

    private DockerClient connectToDocker() {
        try {
            LOG.debug("Connecting to Docker");

            DockerClient docker = DefaultDockerClient.fromEnv().build();
            docker.ping();

            LOG.debug("Connection to Docker established");

            return docker;
        } catch (DockerException | DockerCertificateException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to connect to Docker", e);
        }
    }

    public Optional<Image> findImage(String imageName) {
        DockerClient docker = connectToDocker();
        Optional<Image> images = findImage(docker, imageName);
        docker.close();
        return images;
    }

    private Optional<Image> findImage(DockerClient docker, String imageName) {
        try {
            List<Image> images = docker.listImages(DockerClient.ListImagesParam.allImages());

            for (Image image : images) {
                if (image.repoTags() != null) {
                    for (String tag : image.repoTags()) {
                        if (tag.equals(imageName)) {
                            return Optional.of(image);
                        }
                    }
                }
            }

        } catch (DockerException | InterruptedException e) {
            LOG.error("Unable to retrieve a list of Docker images", e);
        }
        return Optional.empty();
    }

    public Optional<Container> findRunningContainer(String containerName) {
        DockerClient docker = connectToDocker();
        return findExistingContainer(docker, containerName);
    }

    private Optional<Container> findExistingContainer(DockerClient docker, String containerName) {
        try {
            List<Container> containers = docker.listContainers(DockerClient.ListContainersParam.allContainers(true));

            for (Container container : containers) {
                for (String name : container.names()) {
                    if (name.contains(containerName)) {
                        return Optional.of(container);
                    }
                }
            }

        } catch (DockerException | InterruptedException e) {
            LOG.error("Unable to retrieve a list of Docker containers", e);
        }
        return Optional.empty();
    }

    private ContainerCreation createContainer(DockerClient docker) {

        LOG.debug("Creating Couchbase Docker container '{}'", CONTAINER_NAME);

        // Bind container ports to host ports
        final String[] ports = {"8091", "8092", "8093", "8094", "11207", "11210", "11211"};

        final Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            List<PortBinding> hostPorts = new ArrayList<>();
            hostPorts.add(PortBinding.of("0.0.0.0", port));
            portBindings.put(port + "/tcp", hostPorts);
        }

        HostConfig hostConfig = HostConfig.builder()
                .portBindings(portBindings)
                .build();

        // Create container
        ContainerConfig config = ContainerConfig.builder()
                .image(COUCHBASE_LATEST_IMAGE_NAME)
                .hostConfig(hostConfig)
                .build();

        try {
            ContainerCreation container = docker.createContainer(config, CONTAINER_NAME);

            LOG.debug("Couchbase Docker container '{}' created", CONTAINER_NAME);
            return container;
        } catch (DockerException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to create Couchbase Docker container. Is one already running with the same name?", e);
        }
    }

    private void startContainer(DockerClient docker, String containerId) {

        LOG.info("Starting Couchbase container '{}", CONTAINER_NAME);

        try {
            docker.startContainer(containerId);

            LOG.debug("Couchbase container '{}' started", CONTAINER_NAME);
        } catch (DockerException  | InterruptedException e) {
            LOG.error("Unable to start Couchbase container '{}'. Is there something running on the same ports?", CONTAINER_NAME);
            throw new EnvironmentBuilderException("Unable to start Couchbase container", e);
        }
    }

    private void waitForCreateCouchbaseCluster(DockerClient docker, String containerId) {
        StopWatch sw = new StopWatch();
        sw.start();
        boolean success = false;
        EnvironmentBuilderException lastException = null;

        while (sw.getTime() < THIRTY_SECONDS) {
            try {
                createCouchbaseCluster(docker, containerId);
                success = true;
                break;
            } catch (EnvironmentBuilderException e) {
                LOG.debug("Couchbase cluster not yet created after {}, Sleep for {} ms", sw.toString(), POLL_INTERVAL);
                lastException = e;
                sleepFor(POLL_INTERVAL);
            }
        }
        sw.stop();

        if (!success) {
            LOG.debug("Unable to create Couchbase cluster in " + sw.toString(), lastException);
            throw lastException;
        }
    }

    private void sleepFor(long interval) {
        try {
            Thread.sleep(POLL_INTERVAL);
        } catch (InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to wait for Docker container to start", e);
        }
    }

    private void createCouchbaseCluster(DockerClient docker, String containerId) {
        LOG.info("Creating Couchbase cluster");

        try {
            String execId = docker.execCreate(containerId,
                    new String[]{
                            "/opt/couchbase/bin/couchbase-cli"
                            , "setting-cluster"
                            , "-c", "127.0.0.1:8091"
                            , "-u", "admin"
                            , "-p", "password"
                            , "--cluster-name", "couchbase_cluster"
                            , "--cluster-ramsize=500"
                    }
                    , DockerClient.ExecCreateParam.attachStdout(true)
                    , DockerClient.ExecCreateParam.attachStderr(true)
            );

            String log;
            try (final LogStream stream = docker.execStart(execId)) {
                log = stream.readFully();
            }

            final ExecState state = docker.execInspect(execId);

            if (state.exitCode() == 0) {
                LOG.debug("Couchbase cluster created");
            } else {
                LOG.debug("Unable to create Couchbase cluster:\n{}", log);
                throw new EnvironmentBuilderException("Unable to create Couchbase cluster");
            }
        } catch (DockerException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to create Couchbase cluster", e);
        }

    }

    private void initialiseCouchbaseCluster(DockerClient docker, String containerId) {
        LOG.info("Initialising Couchbase cluster");

        try {
            final String execId = docker.execCreate(containerId,
                    new String[]{
                            "/opt/couchbase/bin/couchbase-cli"
                            , "cluster-init"
                            , "-c", "127.0.0.1:8091"
                            , "-u", "admin"
                            , "-p", "password"
                            , "--services=data,index,query"
                            , "--index-storage-setting=default"
                            , "--cluster-ramsize=500"
                            , "--cluster-index-ramsize=500"
                    }
                    , DockerClient.ExecCreateParam.attachStdout(true)
                    , DockerClient.ExecCreateParam.attachStderr(true)
            );

            String log;
            try (final LogStream stream = docker.execStart(execId)) {
                log = stream.readFully();
            }

            final ExecState state = docker.execInspect(execId);

            if (state.exitCode() == 0) {
                LOG.debug("Couchbase cluster initialised");
            } else {
                LOG.error("Unable to initialise Couchbase cluster:\n{}", log);
                throw new EnvironmentBuilderException("Unable to initialise Couchbase cluster");
            }

        } catch (DockerException | InterruptedException e) {
            throw new EnvironmentBuilderException("Unable to initialise Couchbase cluster", e);
        }
    }
}
