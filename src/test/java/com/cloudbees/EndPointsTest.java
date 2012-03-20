/*
 * Copyright © 2012 CloudBees, Inc.
 * This is proprietary code. All rights reserved.
 */

package com.cloudbees;

import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * @author stephenc
 * @since 20/03/2012 13:41
 */
public class EndPointsTest {

    @Before
    public void setUp() {
        System.setProperty("com.cloudbees.Domain", "cloudbees.com");
    }

    @Test
    public void propertySubstitutionHappensAtEvalTime() {
        assertThat(EndPoints.grandCentral(), is("https://grandcentral.cloudbees.com/api"));
        System.setProperty("com.cloudbees.Domain", "beescloud.com");
        assertThat(EndPoints.grandCentral(), is("https://grandcentral.beescloud.com/api"));
    }

    @Test
    public void hooksCanBeUsedByTests() {
        assertThat(EndPoints.grandCentral(), is("https://grandcentral.cloudbees.com/api"));
        EndPoints.hookOverride("com.cloudbees.EndPoints.grandCentral", "I am bob");
        assertThat(EndPoints.grandCentral(), is("I am bob"));
        EndPoints.hookReset();
        assertThat(EndPoints.grandCentral(), is("https://grandcentral.cloudbees.com/api"));
    }
}
