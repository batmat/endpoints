/*
 * Copyright © 2012 CloudBees, Inc.
 * This is proprietary code. All rights reserved.
 */

package com.cloudbees;

/**
 * @author stephenc
 * @since 20/03/2012 15:14
 */
public final class Domain {

    /**
     * Do not instantiate.
     */
    private Domain() {
        throw new IllegalAccessError("Utility class");
    }

    public static String root() {
        return EndPoints.resolveProperty("com.cloudbees.Domain");
    }

    public static String grandCentral() {
        return EndPoints.resolveProperty("com.cloudbees.Domain.grandCentral");
    }
}
