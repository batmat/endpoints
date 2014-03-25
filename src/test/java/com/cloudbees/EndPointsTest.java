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
