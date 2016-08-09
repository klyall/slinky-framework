package org.slinkyframework.environment.builder.maven.plugin;

import org.slinkyframework.environment.builder.EnvironmentManager;

/**
 * @goal setup
 * @phase pre-integration-test
 */
public class EnvironmentSetUpMojo extends AbstractEnvironmentBuilderMojo {

    /**
     * @parameter expression="${env.skipSetup}" default-value="false"
     */
    private boolean skipSetUp;

    public EnvironmentSetUpMojo() {
        super();
    }

    public EnvironmentSetUpMojo(EnvironmentManager environmentManager) {
        super(environmentManager);
    }

    public void setSkipSetUp(boolean skipSetUp) {
        this.skipSetUp = skipSetUp;
    }

    @Override
    void performBuild() {
        if (isSkipTearDown()) {
            getLog().warn("************************************");
            getLog().warn("** Skipping environment tear down **");
            getLog().warn("************************************");

        } else {
            getLog().warn("******************************");
            getLog().warn("** Tearing down environment **");
            getLog().warn("******************************");

            getEnvironmentManager().tearDown(getEnvironmentBuilderContext());
        }

        if (skipSetUp) {
            getLog().warn("********************************");
            getLog().warn("** Skipping environment setup **");
            getLog().warn("********************************");
        } else {
            getLog().warn("******************************");
            getLog().warn("** Setting up environment   **");
            getLog().warn("******************************");

            getEnvironmentManager().setUp(getEnvironmentBuilderContext());
        }
    }
}
