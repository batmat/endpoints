/*
 * Copyright © 2012 CloudBees, Inc.
 * This is proprietary code. All rights reserved.
 */

package com.cloudbees;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides details of all the CloudBees API end-points. The system property {@code com.cloudbees.Domain} can be used
 * to bulk switch all URIs.
 *
 * @author stephenc
 * @since 13/01/2012 14:31
 */
public final class EndPoints {

    /**
     * Singleton lazy initialization pattern.
     */
    private final static class ResourceHolder {
        /**
         * Our properties.
         */
        private static final Properties INSTANCE = loadProperties();

        /**
         * Loads the properties.
         *
         * @return the properties.
         */
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

    /**
     * Do not instantiate.
     */
    private EndPoints() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Gets the GrandCentral API endpoint.
     *
     * @return the GrandCentral API endpoint.
     */
    public static String grandCentral() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.grandCentral"), "/");
    }

    /**
     * Gets the GrandCentral Web UI URL.
     *
     * @return the GrandCentral Web UI URL.
     */
    public static String grandCentralWebUI() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.grandCentralWebUI"), "/");
    }

    /**
     * Gets the Accounts API endpoint.
     *
     * @return the Accounts API endpoint.
     */
    public static String accountsAPI() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.accountAPI"), "/");
    }

    /**
     * Gets the RUN API endpoint.
     *
     * @return the RUN API endpoint.
     */
    public static String runAPI() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.runAPI"), "/");
    }

    /**
     * Gets the licenses API endpoint.
     *
     * @return the licenses API endpoint.
     */
    public static String licenses() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.licenses"), "/");
    }

    /**
     * Gets the OpenId endpoint.
     * @return the OpenId endpoint.
     */
    public static String openId() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.openId"), "/");
    }

    /**
     * @return the forge endpoint template. Using String.format(forgeTemplate(), account) to reify this.
     */
    public static String forgeTemplate() {
        return removeEnd(resolveProperty("com.cloudbees.EndPoints.forgeTemplate"), "/");
    }

    /**
     * @param account the account name for the forge endpoint
     * @return the forge endpoint
     */
    public static String forge(String account) {
        return String.format(forgeTemplate(), account);
    }

    /**
     * Gets the User's URI from the accounts API.
     *
     * @param userId the user id.
     * @return the URI for the specified user.
     */
    public static String accountsAPIUserURI(String userId) {
        return accountsAPI() + "/users/" + userId;
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

    /**
     * Recursively resolves a property value, taking property substitution into account, and allowing System property
     * overrides to take precedence.
     *
     * @param key the key of the property to resolve
     * @return the resolved value.
     */
    static String resolveProperty(String key) {

        StringBuilder result = new StringBuilder();

        String value = System.getProperty(key, ResourceHolder.INSTANCE.getProperty(key));

        if (value == null) {
            return value;
        }

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
            String tmpValue = System.getProperty(tmpKey, ResourceHolder.INSTANCE.getProperty(tmpKey));

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
     *
     * @param str    the source String to search, may be null
     * @param remove the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     *         <code>null</code> if null String input
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
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
