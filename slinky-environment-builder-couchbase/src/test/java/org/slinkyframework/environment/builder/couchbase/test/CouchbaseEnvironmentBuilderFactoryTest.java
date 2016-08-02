package org.slinkyframework.environment.builder.couchbase.test;

import org.junit.Before;
import org.junit.Test;
import org.slinkyframework.environment.builder.EnvironmentBuilder;
import org.slinkyframework.environment.builder.EnvironmentBuilderContext;
import org.slinkyframework.environment.builder.couchbase.local.LocalCouchbaseEnvironmentBuilder;
import org.slinkyframework.environment.builder.couchbase.CouchbaseEnvironmentBuilderFactory;
import org.slinkyframework.environment.builder.couchbase.docker.DockerCouchbaseEnvironmentBuilder;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CouchbaseEnvironmentBuilderFactoryTest {

    private static final String TEST_HOST = "test";

    private CouchbaseEnvironmentBuilderFactory testee;

    @Before
    public void setUp() {
        testee = new CouchbaseEnvironmentBuilderFactory();

    }

    @Test
    public void shouldGetEnvironmentBuilderToBuildLocally() {
        boolean useDocker = false;
        EnvironmentBuilderContext context = new EnvironmentBuilderContext(TEST_HOST, useDocker);

        EnvironmentBuilder environmentBuilder = testee.getInstance(context);

        assertThat("CouchbaseEnvironmentBuilder", environmentBuilder, is(instanceOf(LocalCouchbaseEnvironmentBuilder.class)));
    }

    @Test
    public void shouldGetEnvironmentBuilderToBuildInDocker() {
        boolean useDocker = true;
        EnvironmentBuilderContext context = new EnvironmentBuilderContext(TEST_HOST, useDocker);

        EnvironmentBuilder environmentBuilder = testee.getInstance(context);

        assertThat("CouchbaseEnvironmentBuilder", environmentBuilder, is(instanceOf(DockerCouchbaseEnvironmentBuilder.class)));
    }
}
