package org.slinkyframework.common.aop.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slinkyframework.common.aop.MethodProceedingJoinPoint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MethodProceedingJoinPointDelegateTest {

    @Mock private ProceedingJoinPoint mockProceedingJoinPoint;
    private MethodProceedingJoinPoint testee;

    @Before
    public void setUp() {
        testee = new MethodProceedingJoinPoint(mockProceedingJoinPoint);
    }

    @Test
    public void proceedShouldDelegateToProceedingJoinPoint() throws Throwable {
        testee.proceed();

        verify(mockProceedingJoinPoint).proceed();
    }

    @Test
    public void proceedWithArgsShouldDelegateToProceedingJoinPoint() throws Throwable {
        Object[] arg = new Object[1];

        testee.proceed(arg);

        verify(mockProceedingJoinPoint).proceed(arg);
    }

    @Test
    public void toShortStringShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.toShortString();

        verify(mockProceedingJoinPoint).toShortString();
    }

    @Test
    public void toLongStringShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.toLongString();

        verify(mockProceedingJoinPoint).toLongString();
    }

    @Test
    public void getThisShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getThis();

        verify(mockProceedingJoinPoint).getThis();
    }

    @Test
    public void getTargetShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getTarget();

        verify(mockProceedingJoinPoint).getTarget();
    }

    @Test
    public void getArgsShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getArgs();

        verify(mockProceedingJoinPoint).getArgs();
    }

    @Test
    public void getSignatureShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getSignature();

        verify(mockProceedingJoinPoint).getSignature();
    }

    @Test
    public void getSourceLocationShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getSourceLocation();

        verify(mockProceedingJoinPoint).getSourceLocation();
    }

    @Test
    public void getKindShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getKind();

        verify(mockProceedingJoinPoint).getKind();
    }

    @Test
    public void getStaticPartShouldDelegateToProceedingJoinPoint() throws Exception {
        testee.getStaticPart();

        verify(mockProceedingJoinPoint).getStaticPart();
    }

// TODO Struggling to test this as can't mock Class returned by getWithinType
//    @Test
//    public void getClassNameShould() throws Exception {
//        String expectedClassName = "exampleClass";
//
////        SourceLocation mockSourceLocation = mock(SourceLocation.class);
////        Class mockClass = mock(Class.class);
////
////        when(mockProceedingJoinPoint.getSourceLocation()).thenReturn(mockSourceLocation);
////        when(mockSourceLocation.getWithinType()).thenReturn(mockClass);
////        when(mockClass.getSimpleName()).thenReturn(expectedClassName);
//
//        ProceedingJoinPoint deepMockProceedingJoinPoint = mock(ProceedingJoinPoint.class, RETURNS_DEEP_STUBS);
//        MethodProceedingJoinPoint localTestee = new MethodProceedingJoinPoint(deepMockProceedingJoinPoint);
//
//        when(deepMockProceedingJoinPoint.getSourceLocation().getWithinType().getSimpleName()).thenReturn(expectedClassName);
//
//        String className = localTestee.getClassName();
//
//        assertThat("className", className, is(equalTo(expectedClassName)));
//    }

    @Test
    public void getMethodNameShould() throws Exception {

        String expectedMethodName = "doStuff";

        ProceedingJoinPoint deepMockProceedingJoinPoint = mock(ProceedingJoinPoint.class, RETURNS_DEEP_STUBS);
        MethodProceedingJoinPoint localTestee = new MethodProceedingJoinPoint(deepMockProceedingJoinPoint);

        when(deepMockProceedingJoinPoint.getSignature().getName()).thenReturn(expectedMethodName);

        String actualMethodName = localTestee.getMethodName();

        assertThat("methodName", actualMethodName, is(equalTo(expectedMethodName)));
    }
}