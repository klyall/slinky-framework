package org.slinkyframework.common.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.codahale.metrics.MetricRegistry.name;
import static java.lang.String.format;

public abstract class AbstractMetricsAspect {

    public static final String METRICS_REGISTRY_NAME = "slinky-metrics";

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMetricsAspect.class);
    private static final MetricRegistry metrics = SharedMetricRegistries.getOrCreate(METRICS_REGISTRY_NAME);

    private final Map<String, Timer> timers = Collections.synchronizedMap(new HashMap<String, Timer>());

    protected abstract String getComponentType();

    public Object metricsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodProceedingJoinPoint methodProceedingJoinPoint = new MethodProceedingJoinPoint(proceedingJoinPoint);

        Timer timer = getTimer(methodProceedingJoinPoint);
        final Timer.Context context = timer.time();
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            context.stop();
        }
    }

    private Timer getTimer(MethodProceedingJoinPoint methodProceedingJoinPoint) {

        synchronized (timers) {
            String key = createKey(methodProceedingJoinPoint);
            if (!timers.containsKey(key)) {
                final Timer timer = metrics.timer(createMetricName(methodProceedingJoinPoint));
                timers.put(key, timer);
                LOGGER.debug("Created timer with key {}", key);
            }
            return timers.get(key);
        }
    }

    private String createKey(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        return format("%s.%s.%s",
                getComponentType(),
                methodProceedingJoinPoint.getClassName(),
                methodProceedingJoinPoint.getMethodName());
    }

    private String createMetricName(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        return name(
                getComponentType(),
                methodProceedingJoinPoint.getClassName(),
                methodProceedingJoinPoint.getMethodName());
    }
}
