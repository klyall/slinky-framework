package org.slinkyframework.environment.config.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slinkyframework.environment.config.maven.plugin.info.ConfigDirectoryWalker;

import java.io.File;

@Mojo(name = "info")
public class EnvironmentConfigInfoMojo extends AbstractMojo {

    @Parameter(property = "config.sourceDir", defaultValue = "src/main/resources", readonly = true)
    private String sourceDir;

    public EnvironmentConfigInfoMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ConfigDirectoryWalker directoryWalker = new ConfigDirectoryWalker();
        directoryWalker.walk(new File(sourceDir));
    }
}
