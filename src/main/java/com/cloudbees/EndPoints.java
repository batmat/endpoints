/*
 * Copyright © 2012 CloudBees, Inc.
 * This is proprietary code. All rights reserved.
 */

package com.cloudbees;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides details of all the CloudBees API end-points.
 *
 * @author stephenc
 * @since 13/01/2012 14:31
 */
public final class EndPoints {

    /**
     * Singleton lazy initialization pattern.
     */
    private final static class ResourceHolder {
        private static final Properties INSTANCE = loadProperties();

        private static Properties loadProperties() {
            Properties properties = new Properties();
            InputStream inputStream = EndPoints.class
                    .getResourceAsStream("/" + EndPoints.class.getName().replace('.', '/') + ".properties");
            if (inputStream != null) {
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    // ignore
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
            return properties;
        }
    }

    public static String grandCentral() {
        return removeEnd(resolveProperty("GrandCentral"), "/");
    }

    public static String gcApi() {
        return removeEnd(resolveProperty("GCApi"), "/");
    }

    public static String runAPI() {
        return removeEnd(resolveProperty("RunAPI"), "/");
    }

    public static String licenses() {
        return removeEnd(resolveProperty("Licenses"), "/");
    }

    /**
     * @return the forge endpoint template. Using String.format(forgeTemplate(), account) to reify this.
     */
    public static String forgeTemplate() {
        return removeEnd(resolveProperty("Forge"), "/");
    }

    /**
     * @param account the account name for the forge endpoint
     * @return the forge endpoint
     */
    public static String forge(String account) {
        return String.format(forgeTemplate(), account);
    }

    public static String gcApiUserUri(String userId) {
        return gcApi() + "/users/" + userId;
    }

    /**
     * A test support hook to reset the EndPoints to the defaults
     */
    public static void hookReset() {
        ResourceHolder.INSTANCE.clear();
        ResourceHolder.INSTANCE.putAll(ResourceHolder.loadProperties());
    }

    /**
     * A test support hook to override a specific property.
     *
     * @param key   the key.
     * @param value the value.
     */
    public static void hookOverride(String key, String value) {
        ResourceHolder.INSTANCE.setProperty(key, value);
    }

    private static String resolveProperty(String key) {

        StringBuilder result = new StringBuilder();

        String value = ResourceHolder.INSTANCE.getProperty(key);

        int i1, i2;

        while ((i1 = value.indexOf("${")) >= 0) {
            // append prefix to result
            result.append(value.substring(0, i1));

            // strip prefix from original
            value = value.substring(i1 + 2);

            // if no matching } then bail
            if ((i2 = value.indexOf('}')) < 0) {
                break;
            }

            // strip out the key and resolve it
            // resolve the key/value for the ${statement}
            String tmpKey = value.substring(0, i2);
            value = value.substring(i2 + 1);
            String tmpValue = ResourceHolder.INSTANCE.getProperty(tmpKey);

            // try global environment..
            if (tmpValue == null && !isEmpty(tmpKey) ) {
                tmpValue = System.getProperty(tmpKey);
            }

            // if the key cannot be resolved,
            // leave it alone ( and don't parse again )
            // else prefix the original string with the
            // resolved property ( so it can be parsed further )
            // taking recursion into account.
            if (tmpValue == null || tmpValue.equals(key) || key.equals(tmpKey)) {
                result.append("${").append(tmpKey).append("}");
            } else {
                value = tmpValue + value;
            }
        }
        result.append(value);
        return result.toString();
    }

    /**
     * <p>Removes a substring only if it is at the end of a source string,
     * otherwise returns the source string.</p>
     * @param str  the source String to search, may be null
     * @param remove  the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     *  <code>null</code> if null String input
     */
    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
