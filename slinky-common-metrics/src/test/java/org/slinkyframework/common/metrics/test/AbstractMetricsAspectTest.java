package org.slinkyframework.common.metrics.test;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slinkyframework.common.metrics.test.example.ExampleClass;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.slinkyframework.common.metrics.AbstractMetricsAspect.TAG_METHOD;

public class AbstractMetricsAspectTest {

    private static SimpleMeterRegistry registry = new SimpleMeterRegistry();

    @BeforeClass
    public static void setUp() {
        Metrics.addRegistry(registry);
    }

    @Test
    public void shouldRecordMetricsForNormalExecution() throws UnknownHostException {
        ExampleClass exampleClass = new ExampleClass();

        for (int i = 0; i < 10; i++) {
            exampleClass.exampleMethod();
        }

        Timer timer = findTimer("exampleMethod");

        assertThat("Metric name", timer.getId().getName(), is("example.requests"));
        assertThat("Metric tag count", timer.getId().getTags(), hasSize(4));
        assertThat("Metric class tag", timer.getId().getTag("class"), is("ExampleClass"));
        assertThat("Metric method tag", timer.getId().getTag("method"), is("exampleMethod"));
        assertThat("Metric hostname tag", timer.getId().getTag("hostname"), is(InetAddress.getLocalHost().getHostName()));
        assertThat("Metric exception tag", timer.getId().getTag("exception"), is("None"));

        assertThat("Metrics count", timer.count(), is(10L));
    }

    @Test
    public void shouldRecordMetricsForAnException() throws UnknownHostException {
        ExampleClass exampleClass = new ExampleClass();

        try {
            exampleClass.exampleExceptionMethod();
        } catch (IllegalArgumentException e) {
            // Ignore
        }

        Timer timer = findTimer("exampleExceptionMethod");

        System.out.println(timer.getId());

        assertThat("Metric name", timer.getId().getName(), is("example.requests"));
        assertThat("Metric tag count", timer.getId().getTags(), hasSize(4));
        assertThat("Metric class tag", timer.getId().getTag("class"), is("ExampleClass"));
        assertThat("Metric method tag", timer.getId().getTag("method"), is("exampleExceptionMethod"));
        assertThat("Metric hostname tag", timer.getId().getTag("hostname"), is(InetAddress.getLocalHost().getHostName()));
        assertThat("Metric exception tag", timer.getId().getTag("exception"), is("java.lang.IllegalArgumentException"));

        assertThat("Metrics count", timer.count(), is(1L));
    }

    private Timer findTimer(String methodName) {
        return (Timer) registry.getMeters().stream()
                .filter(t -> t.getId().getTag(TAG_METHOD).equals(methodName))
                .findFirst()
                .orElseThrow(() ->new IllegalStateException(format("Metric '%s' not found", methodName)));
    }
}