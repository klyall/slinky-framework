package org.slinkyframework.environment.builder.couchbase.test.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.environment.builder.couchbase.CouchbaseBuildDefinition;
import org.slinkyframework.environment.builder.couchbase.docker.DockerCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slinkyframework.environment.builder.couchbase.matchers.CouchbaseMatchers.hasPortAvailable;

@RunWith(MockitoJUnitRunner.class)
public class DockerCouchbaseEnvironmentBuilderIntegrationTest {

    private static final String TEST_HOST = "dev";
    private static final String TEST_BUCKET_1_NAME = "testBucket1";
    private static final String TEST_BUCKET_1_PASSWORD = "password1";
    private static final String TEST_BUCKET_2_NAME = "testBucker2";
    private static final String TEST_BUCKET_2_PASSWORD = "password2";
    private static final String TEST_DOCUMENT_PACKAGE = "org.example";
    private static final String TEST_DOCUMENT_CLASS_NAME = "ExampleDocument";

    @Mock LocalCouchbaseEnvironmentBuilder mockLocalCouchbaseEnvironmentBuilder;

    private DockerCouchbaseEnvironmentBuilder testee;

    private Set<CouchbaseBuildDefinition> buildDefinitions;
    private CouchbaseBuildDefinition definition1;
    private CouchbaseBuildDefinition definition2;
    private DockerClient docker;

    @Before
    public void setUp() throws DockerCertificateException {

        docker = DefaultDockerClient.fromEnv().build();

        testee = new DockerCouchbaseEnvironmentBuilder(mockLocalCouchbaseEnvironmentBuilder);
        buildDefinitions = new TreeSet<>();
        definition1 = new CouchbaseBuildDefinition("Definition1", TEST_BUCKET_1_NAME, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
        definition1.setBucketPassword(TEST_BUCKET_1_PASSWORD);

        definition2 = new CouchbaseBuildDefinition("Definition2", TEST_BUCKET_2_NAME, TEST_DOCUMENT_PACKAGE, TEST_DOCUMENT_CLASS_NAME);
        definition2.setBucketPassword(TEST_BUCKET_2_PASSWORD);

        // Make sure no Docker containers left lying around
        testee.tearDown(buildDefinitions);

        reset(mockLocalCouchbaseEnvironmentBuilder);

        String[] hosts = {TEST_HOST};
        when(mockLocalCouchbaseEnvironmentBuilder.getHosts()).thenReturn(hosts);
    }

    @Test
    public void shouldDelegateToLocalCouchbaseEnvironmentBuilderSetUp() {
        testee.setUp(buildDefinitions);

        verify(mockLocalCouchbaseEnvironmentBuilder).setUp(buildDefinitions);
    }

    @Test
    public void shouldCallToLocalCouchbaseEnvironmentBuilderTearDown() {
        testee.tearDown(buildDefinitions);

        verify(mockLocalCouchbaseEnvironmentBuilder).tearDown(buildDefinitions);
    }

    @Test
    public void shouldCreateThenStartThenConfigureAContainer() {
        testee.setUp(buildDefinitions);

        assertThat("Container found", testee.findRunningContainer(DockerCouchbaseEnvironmentBuilder.CONTAINER_NAME).isPresent(), is(true));
        assertThat("Port 8091 available", TEST_HOST, hasPortAvailable(8091));
        assertThat("Port 8092 available", TEST_HOST, hasPortAvailable(8092));
        assertThat("Port 8093 available", TEST_HOST, hasPortAvailable(8093));
        assertThat("Port 8094 available", TEST_HOST, hasPortAvailable(8094));
    }

    @Test
    public void shouldSkipCreateAndStartAndJustConfigureARunningContainer() {
        testee.setUp(buildDefinitions);
        testee.setUp(buildDefinitions);

        assertThat("Container found", testee.findRunningContainer(DockerCouchbaseEnvironmentBuilder.CONTAINER_NAME).isPresent(), is(true));
    }

    @Test
    public void shouldTearDownARunningContainer() {
        testee.setUp(buildDefinitions);
        testee.tearDown(buildDefinitions);

        assertThat("Container found", testee.findRunningContainer(DockerCouchbaseEnvironmentBuilder.CONTAINER_NAME).isPresent(), is(false));
    }

    @Test
    @Ignore("Takes a long time to run. So ignoring for main run.")
    public void shouldPullDownCouchbaseImageIfOneDoesNotExistLocally() throws Exception {
        removeExistingImage();

        testee.setUp(buildDefinitions);

        assertThat("Container found", testee.findRunningContainer(DockerCouchbaseEnvironmentBuilder.CONTAINER_NAME).isPresent(), is(true));
    }

    private void removeExistingImage() throws DockerException, InterruptedException {
        Optional<Image> image = testee.findImage(DockerCouchbaseEnvironmentBuilder.COUCHBASE_LATEST_IMAGE_NAME);

        if (image.isPresent()) {
            boolean force = true;
            boolean noPrune = false;

            docker.removeImage(image.get().id(), force, noPrune);
        }
    }
}
