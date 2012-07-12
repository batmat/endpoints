/*
 * Copyright © 2012 CloudBees, Inc.
 * This is proprietary code. All rights reserved.
 */

package com.cloudbees;

/**
 * Provides details of all the CloudBees domain names.
 *
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

    /**
     * The base domain hosting CloudBees services, e.g. <code>cloudbees.com</code> or <code>beescloud.com</code>
     *
     * @return The base domain hosting CloudBees services, e.g. <code>cloudbees.com</code> or <code>beescloud.com</code>
     */
    public static String root() {
        return EndPoints.resolveProperty("com.cloudbees.Domain");
    }

    /**
     * The Grand Central server domain name.
     *
     * @return The Grand Central server domain name.
     */
    public static String grandCentral() {
        return EndPoints.resolveProperty("com.cloudbees.Domain.grandCentral");
    }
}
