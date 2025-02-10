package com.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
public class ExceptionUtilsTest {


    @Test
    void printStacktrace() {
        try {
            throw new RuntimeException("Test");
        } catch (Exception e) {
            /**
             * java.lang.RuntimeException: Test
             * 	at com.exceptions.ExceptionUtils.printStacktrace(ExceptionUtils.java:13)
             * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
             * 	at org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)
             */
            // solution 1
            log.info("--------------- Solution 2 ------------------------");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String stacktrace = stringWriter.toString();
            log.info(stacktrace);

            /**
             * --------------- Solution 2 ------------------------
             * java.lang.RuntimeException: Test
             * 	at com.exceptions.ExceptionUtilsTest.printStacktrace(ExceptionUtilsTest.java:24)
             * 	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
             * 	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
             */
            // solution 2 - ExceptionUtils from apache commons-lang3
            log.info("--------------- Solution 2 ------------------------");
            String stacktrace2 = ExceptionUtils.getStackTrace(e);
            log.info(stacktrace2);
        }
    }
}
