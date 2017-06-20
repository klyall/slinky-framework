package org.slinkyframework.environment.builder.maven.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slinkyframework.environment.builder.EnvironmentManager;

@Mojo(name = "setup", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, inheritByDefault = true)
public class EnvironmentSetUpMojo extends AbstractEnvironmentBuilderMojo {

    @Parameter(property = "env.skipSetup", defaultValue = "false", readonly = true)
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
