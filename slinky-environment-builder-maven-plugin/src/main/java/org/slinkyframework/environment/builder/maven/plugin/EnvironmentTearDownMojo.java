package org.slinkyframework.environment.builder.maven.plugin;

import org.slinkyframework.environment.builder.EnvironmentManager;

/**
 * @goal teardown
 * @phase post-integration-test
 */
public class EnvironmentTearDownMojo extends AbstractEnvironmentBuilderMojo {

    public EnvironmentTearDownMojo() {
        super();
    }

    public EnvironmentTearDownMojo(EnvironmentManager environmentManager) {
        super(environmentManager);
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
    }
}
