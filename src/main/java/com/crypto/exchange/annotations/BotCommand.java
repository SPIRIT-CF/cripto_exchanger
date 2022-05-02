package com.crypto.exchange.annotations;

import com.crypto.exchange.Command;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface BotCommand {

    Command[] command();

/*    *//**
     * Returns an array of user roles that have access to the handler
     * Default: {@link Role#UNAUTHORIZED} - every user can call this handler
     *
     * @return an array of user roles that have access to the handler
     *//*
    Role[] requiredRoles() default UNAUTHORIZED;*/
}

