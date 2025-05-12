package com.hwoolog.api.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = HwoologMockSecurityContext.class)
public @interface HwoologMockUser {

    String name() default "hwoo";

    String email() default "aaa@aaa.com";

    String password() default "";

    // String role() default "ROLE_ADMIN";
}