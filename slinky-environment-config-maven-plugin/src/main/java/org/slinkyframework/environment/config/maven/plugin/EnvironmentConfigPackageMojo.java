package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slinkyframework.environment.config.maven.plugin.zip.ZipFileFactory;

import java.io.File;

@Mojo(name = "package", defaultPhase = LifecyclePhase.PACKAGE)
@Execute(goal = "package", phase = LifecyclePhase.PACKAGE)
public class EnvironmentConfigPackageMojo extends AbstractMojo {

    @Parameter(property = "config.targetDir", defaultValue = "target/generated-config", readonly = true)
    private String targetDir;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public EnvironmentConfigPackageMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ZipFileFactory zipFileFactory = new ZipFileFactory(new File(targetDir), project.getVersion());
        zipFileFactory.createZipFiles();
    }
}
