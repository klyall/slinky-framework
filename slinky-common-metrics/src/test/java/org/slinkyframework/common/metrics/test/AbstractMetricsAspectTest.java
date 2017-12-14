package org.slinkyframework.common.metrics.test;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import org.junit.Test;
import org.slinkyframework.common.metrics.AbstractMetricsAspect;
import org.slinkyframework.common.metrics.test.example.ExampleClass;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class AbstractMetricsAspectTest {

    @Test
    public void shouldCollectMetrics() throws InterruptedException {

        MetricRegistry metrics = SharedMetricRegistries.getOrCreate(AbstractMetricsAspect.METRICS_REGISTRY_NAME);

        ExampleClass exampleClass = new ExampleClass();

        for (int i = 0; i < 10; i++) {
            exampleClass.exampleMethod();
        }

        assertThat("Metrics count", metrics.getMetrics().keySet(), hasSize(1));
    }
}