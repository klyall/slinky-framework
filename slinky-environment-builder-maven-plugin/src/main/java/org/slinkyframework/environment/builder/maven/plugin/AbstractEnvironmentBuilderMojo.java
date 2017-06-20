package org.slinkyframework.environment.builder.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.EnvironmentManager;
import org.slinkyframework.environment.builder.EnvironmentManagerImpl;

public abstract class AbstractEnvironmentBuilderMojo extends AbstractMojo {

    @Parameter(property = "env.host", defaultValue = "localhost", readonly = true)
    private String host;

    @Parameter(property = "env.docker", defaultValue = "false", readonly = true)
    private boolean useDocker;

    @Parameter(property = "env.skipTearDown", defaultValue = "false", readonly = true)
    private boolean skipTearDown;

    private EnvironmentManager environmentManager;

    public AbstractEnvironmentBuilderMojo() {
        this(new EnvironmentManagerImpl());
    }

    public AbstractEnvironmentBuilderMojo(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
    }

    abstract void performBuild();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        performBuild();
    }

    protected EnvironmentManager getEnvironmentManager() {
        return environmentManager;
    }

    public EnvironmentBuilderContext getEnvironmentBuilderContext() {
        return new EnvironmentBuilderContext(host, useDocker);
    }

    public void setUseDocker(boolean useDocker) {
        this.useDocker = useDocker;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSkipTearDown(boolean skipTearDown) {
        this.skipTearDown = skipTearDown;
    }

    public boolean isSkipTearDown() {
        return skipTearDown;
    }
}
