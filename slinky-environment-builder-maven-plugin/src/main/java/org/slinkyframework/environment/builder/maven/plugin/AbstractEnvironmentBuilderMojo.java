package org.slinkyframework.environment.builder.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.EnvironmentManager;
import org.slinkyframework.environment.builder.EnvironmentManagerImpl;

public abstract class AbstractEnvironmentBuilderMojo extends AbstractMojo {

    /**
     * @parameter expression="${env.host}" default-value="localhost"
     */
    private String host;

    /**
     * @parameter expression="${env.docker}" default-value="false"
     */
    private boolean useDocker;


    /**
     * @parameter expression="${env.skipTearDown}" default-value="false"
     */
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
