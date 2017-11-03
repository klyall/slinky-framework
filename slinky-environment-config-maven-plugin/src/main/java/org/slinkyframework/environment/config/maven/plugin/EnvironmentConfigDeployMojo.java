package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.model.DeploymentRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slinkyframework.environment.config.maven.plugin.deploy.MavenDeployer;

import java.io.File;
import java.util.Optional;

@Mojo(name = "deploy", defaultPhase = LifecyclePhase.DEPLOY)
public class EnvironmentConfigDeployMojo extends AbstractMojo {

    @Parameter(property = "config.targetDir", defaultValue = "target/generated-config", readonly = true)
    private String targetDir;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public EnvironmentConfigDeployMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Optional<DeploymentRepository> repository = getDeploymentRepository();

        MavenDeployer mavenDeployer;

        if (repository.isPresent()) {
            mavenDeployer = new MavenDeployer(project.getBasedir(), project.getGroupId(), project.getVersion(), new File(targetDir), repository.get());
        } else {
            mavenDeployer = new MavenDeployer(project.getBasedir(), project.getGroupId(), project.getVersion(), new File(targetDir));
        }

        mavenDeployer.processEnvironments();
    }

    private Optional<DeploymentRepository> getDeploymentRepository() {
        boolean isSnapshot = isSnapshot();

        if (isSnapshot) {
            return Optional.of(project.getDistributionManagement().getSnapshotRepository());
        } else {
            return Optional.of(project.getDistributionManagement().getRepository());

        }
    }

    private boolean isSnapshot() {
        String version = project.getVersion();

        if (version != null && version.endsWith("SNAPSHOT")) {
            return true;
        } else {
            return false;
        }
    }
}
