package org.slinkyframework.environment.builder.maven.plugin.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.EnvironmentManager;
import org.slinkyframework.environment.builder.maven.plugin.EnvironmentTearDownMojo;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentTearDownMojoTest {

    @Mock private EnvironmentManager mockEnvironmentManager;

    private EnvironmentTearDownMojo testee;
    private EnvironmentBuilderContext defaultTestContext = new EnvironmentBuilderContext("localhost", false);

    @Before
    public void setUp() {
        testee = new EnvironmentTearDownMojo(mockEnvironmentManager);
        testee.setHost("localhost");
        testee.setUseDocker(false);
    }

    @Test
    public void shouldCallEnvironmentManager() throws Exception {

        testee.execute();

        verify(mockEnvironmentManager).tearDown(defaultTestContext);
    }
}
