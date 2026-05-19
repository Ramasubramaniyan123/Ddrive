package com.learning.spring.aspectxml;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AuditInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 1. Log BEFORE
        System.out.println(">>> [Start] Method: " + invocation.getMethod().getName());

        try {
            // 2. PROCEED (Run the actual method)
            Object result = invocation.proceed();

            // 3. Log AFTER (Success)
            System.out.println(">>> [End] Method execution finished successfully.\n");

            return result;
        } catch (Exception e) {
            // (Optional) Log AFTER (Exception)
            System.out.println(">>> [Error] Method failed!");
            throw e;
        }
    }
}
