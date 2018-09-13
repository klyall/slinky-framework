package org.slinkyframework.common.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public abstract class AbstractMetricsAspect {

    private static final String TAG_CLASS = "class";
    public static final String TAG_METHOD = "method";
    private static final String TAG_HOSTNAME = "hostname";
    private static final String TAG_EXCEPTION = "exception";
    private static final String UNKNOWN_HOSTNAME = "unknown";

    private static final String METRIC_NAME = "%s.requests";

    protected abstract String getComponentType();

    public Object metricsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result;
        MethodProceedingJoinPoint methodProceedingJoinPoint = new MethodProceedingJoinPoint(proceedingJoinPoint);

        final Clock clock = Metrics.globalRegistry.config().clock();
        final long startTime = clock.monotonicTime();

        try {
            result = proceedingJoinPoint.proceed();

            getTimerBuilder(methodProceedingJoinPoint, null)
                    .register(Metrics.globalRegistry)
                    .record(clock.monotonicTime() - startTime, TimeUnit.NANOSECONDS);

        } catch (Throwable e) {

            getTimerBuilder(methodProceedingJoinPoint, e)
                    .register(Metrics.globalRegistry)
                    .record(clock.monotonicTime() - startTime, TimeUnit.NANOSECONDS);

            throw e;
        }
        return result;
    }

    private Timer.Builder getTimerBuilder(MethodProceedingJoinPoint methodProceedingJoinPoint, Throwable throwable) {
        return Timer.builder(format(METRIC_NAME, getComponentType()))
                .tags(createTags(methodProceedingJoinPoint, throwable))
                .description(format("Timer of %s operations", getComponentType()));
    }

    private List<Tag> createTags(MethodProceedingJoinPoint methodProceedingJoinPoint, Throwable throwable) {
        return Arrays.asList(
                getClassNameTag(methodProceedingJoinPoint),
                getMethodNameTag(methodProceedingJoinPoint),
                getExceptionTag(throwable),
                getHostNameTag());
    }

    private Tag getClassNameTag(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        return Tag.of(TAG_CLASS, methodProceedingJoinPoint.getClassName());
    }

    private Tag getMethodNameTag(MethodProceedingJoinPoint methodProceedingJoinPoint) {
        return Tag.of(TAG_METHOD, methodProceedingJoinPoint.getMethodName());
    }

    private Tag getHostNameTag() {
        String hostname;

        try {
            hostname =  InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = UNKNOWN_HOSTNAME;
        }
        return Tag.of(TAG_HOSTNAME, hostname);
    }

    private Tag getExceptionTag(Throwable throwable) {
        String name = "None";

        if (throwable != null) {
            name = throwable.getClass().getName();
        }
        return Tag.of(TAG_EXCEPTION, name);
    }
}
