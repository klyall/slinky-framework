package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slinkyframework.environment.config.maven.plugin.install.MavenInstaller;

import java.io.File;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
public class EnvironmentConfigInstallMojo extends AbstractMojo {

    @Parameter(property = "config.targetDir", defaultValue = "target/generated-config", readonly = true)
    private String targetDir;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public EnvironmentConfigInstallMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MavenInstaller mavenInstaller = new MavenInstaller(project.getBasedir(), project.getGroupId(), project.getVersion(), new File(targetDir));
        mavenInstaller.processEnvironments();
    }
}
