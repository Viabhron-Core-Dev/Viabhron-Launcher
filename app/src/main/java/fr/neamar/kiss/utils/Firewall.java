package fr.neamar.kiss.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements a default-deny firewall for network calls and resource monitoring.
 */
public class Firewall {

    private static final Set<String> WHITELISTED_URLS = new HashSet<>();
    private static final Set<String> WHITELISTED_PLUGINS = new HashSet<>();
    private static boolean isEnabled = true;

    static {
        // Whitelist system-critical URLs
        WHITELISTED_URLS.add("https://api.github.com");
        WHITELISTED_URLS.add("https://play.google.com");

        // Whitelist core plugins (placeholder)
        WHITELISTED_PLUGINS.add("viabhron-core");
    }

    /**
     * Checks if a network call is allowed.
     * @param url The URL to check
     * @param pluginId The plugin ID making the call (null if system call)
     * @return true if the call is allowed, false otherwise
     */
    public static boolean isNetworkCallAllowed(String url, String pluginId) {
        if (!isEnabled) {
            return true; // Default-allow if firewall is disabled
        }

        // Allow whitelisted URLs
        if (WHITELISTED_URLS.contains(url)) {
            return true;
        }

        // Allow whitelisted plugins
        if (pluginId != null && WHITELISTED_PLUGINS.contains(pluginId)) {
            return true;
        }

        // Default-deny
        return false;
    }

    /**
     * Blocks a network call.
     * @param url The URL to block
     * @param pluginId The plugin ID making the call
     */
    public static void blockNetworkCall(String url, String pluginId) {
        // In a real implementation, this would log the blocked call
        NotificationManager.sendNetworkBlockAlert(url);
    }

    /**
     * Enables the firewall.
     */
    public static void enable() {
        isEnabled = true;
    }

    /**
     * Disables the firewall.
     */
    public static void disable() {
        isEnabled = false;
    }

    /**
     * Checks if the firewall is enabled.
     * @return true if enabled, false otherwise
     */
    public static boolean isEnabled() {
        return isEnabled;
    }
}