package com.moblin.groovyh2.util;

import com.moblin.groovyh2.BuildConfig;

/**
 * Collection of some assertion methods. These methods throw AssertionError
 * in certain conditions but only in the debug mode.
 */
public class Assert {
    /**
     * Checks that the provided expression is true. Throws an exception
     * in debug mode.
     * @param expression - tested assumption
     * @param detailMessage - error message
     */
    public static void isTrue(boolean expression, String detailMessage) {
        if (BuildConfig.DEBUG && !expression) {
            throw new AssertionError(detailMessage);
        }
    }

    /**
     * Checks that the provided object reference is not NULL. Throws an exception
     * in debug mode.
     * @param object - tested object reference
     * @param detailMessage - error message
     */
    public static void notNull(Object object, String detailMessage) {
        if (BuildConfig.DEBUG && object == null) {
            throw new AssertionError(detailMessage);
        }
    }
}

