package org.slinkyframework.client.test.metrics;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slinkyframework.client.test.example.ExampleClient;
import org.slinkyframework.client.test.example.ExampleClientImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SlinkyClientMetricsAspectTest {

    private static SimpleMeterRegistry registry = new SimpleMeterRegistry();

    @BeforeClass
    public static void setUp() {
        Metrics.addRegistry(registry);
    }

    @Test
    public void shouldRecordMetricsForNormalExecution() throws UnknownHostException {
        ExampleClient testee = new ExampleClientImpl();

        for (int i = 0; i < 10; i++) {
            testee.retrieveAccountDetails(String.valueOf(i));
        }

        Timer timer = (Timer) registry.getMeters().get(0);

        assertThat("Metric name", timer.getId().getName(), is("client.requests"));
        assertThat("Metric tag count", timer.getId().getTags(), hasSize(4));
        assertThat("Metric class tag", timer.getId().getTag("class"), is("ExampleClient"));
        assertThat("Metric method tag", timer.getId().getTag("method"), is("retrieveAccountDetails"));
        assertThat("Metric hostname tag", timer.getId().getTag("hostname"), is(InetAddress.getLocalHost().getHostName()));
        assertThat("Metric exception tag", timer.getId().getTag("exception"), is("None"));

        assertThat("Metrics count", timer.count(), is(10L));
    }
}
