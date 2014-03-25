/*
 * The MIT License
 *
 * Copyright 2014 CloudBees.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
