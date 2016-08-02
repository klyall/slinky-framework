package org.slinkyframework.environment.builder.maven.plugin.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.EnvironmentManager;
import org.slinkyframework.environment.builder.maven.plugin.EnvironmentSetUpMojo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentSetUpMojoTest {

    private static final String TEST_HOST = "localhost";
    private static final boolean TEST_USE_DOCKER = false;

    @Mock private EnvironmentManager mockEnvironmentManager;

    private EnvironmentSetUpMojo testee;
    private EnvironmentBuilderContext testContext = new EnvironmentBuilderContext(TEST_HOST, TEST_USE_DOCKER);

    @Before
    public void setUp() {
        testee = new EnvironmentSetUpMojo(mockEnvironmentManager);
        testee.setSkipTearDown(false);
        testee.setSkipSetUp(false);
        testee.setHost(TEST_HOST);
        testee.setUseDocker(TEST_USE_DOCKER);
    }

    @Test
    public void shouldCallTearDownThenSetUp() throws Exception {
        testee.execute();

        verify(mockEnvironmentManager).tearDown(testContext);
        verify(mockEnvironmentManager).setUp(testContext);
    }

    @Test
    public void shouldSkipTearDownThenDoSetUp() throws Exception {
        testee.setSkipTearDown(true);

        testee.execute();

        verify(mockEnvironmentManager, never()).tearDown(any(EnvironmentBuilderContext.class));
        verify(mockEnvironmentManager).setUp(testContext);
    }

    @Test
    public void shouldCallTearDownThenSkipSetUp() throws Exception {
        testee.setSkipSetUp(true);

        testee.execute();

        verify(mockEnvironmentManager).tearDown(testContext);
        verify(mockEnvironmentManager, never()).setUp(any(EnvironmentBuilderContext.class));
    }

    @Test
    public void shouldSkipBothTearDownAndSetUp() throws Exception {
        testee.setSkipTearDown(true);
        testee.setSkipSetUp(true);

        testee.execute();

        verify(mockEnvironmentManager, never()).tearDown(any(EnvironmentBuilderContext.class));
        verify(mockEnvironmentManager, never()).setUp(any(EnvironmentBuilderContext.class));
    }}
