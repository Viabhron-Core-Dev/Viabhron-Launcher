package fr.neamar.kiss.loader;

import fr.neamar.kiss.pojo.PluginCertificate;
import fr.neamar.kiss.pojo.PluginManifest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the lifecycle of Viabhron plugins.
 */
public class PluginManager {

    private final Map<String, PluginLoader.Plugin> enabledPlugins = new HashMap<>();
    private final Map<String, PluginLoader.Plugin> disabledPlugins = new HashMap<>();

    /**
     * Loads all plugins from the default directory.
     */
    public void loadAllPlugins() {
        String pluginDir = "/sdcard/Viabhron/plugins/";
        List<PluginLoader.Plugin> plugins = PluginLoader.loadPlugins(pluginDir);

        for (PluginLoader.Plugin plugin : plugins) {
            disabledPlugins.put(plugin.getManifest().getName(), plugin);
        }
    }

    /**
     * Enables a plugin by its name.
     * @param pluginName Name of the plugin to enable
     */
    public void enablePlugin(String pluginName) {
        PluginLoader.Plugin plugin = disabledPlugins.remove(pluginName);
        if (plugin != null) {
            enabledPlugins.put(pluginName, plugin);
        }
    }

    /**
     * Disables a plugin by its name.
     * @param pluginName Name of the plugin to disable
     */
    public void disablePlugin(String pluginName) {
        PluginLoader.Plugin plugin = enabledPlugins.remove(pluginName);
        if (plugin != null) {
            disabledPlugins.put(pluginName, plugin);
        }
    }

    /**
     * Applies the killswitch to a plugin by its name.
     * @param pluginName Name of the plugin to kill
     */
    public void killSwitch(String pluginName) {
        PluginLoader.Plugin plugin = enabledPlugins.remove(pluginName);
        if (plugin != null) {
            // In a real implementation, this would stop the plugin's processes
            disabledPlugins.put(pluginName, plugin);
        }
    }

    /**
     * Gets a loaded plugin by its name.
     * @param pluginName Name of the plugin
     * @return Plugin object if found, null otherwise
     */
    public PluginLoader.Plugin getPlugin(String pluginName) {
        PluginLoader.Plugin plugin = enabledPlugins.get(pluginName);
        if (plugin == null) {
            plugin = disabledPlugins.get(pluginName);
        }
        return plugin;
    }

    /**
     * Gets all enabled plugins.
     * @return Map of enabled plugin names to Plugin objects
     */
    public Map<String, PluginLoader.Plugin> getEnabledPlugins() {
        return new HashMap<>(enabledPlugins);
    }

    /**
     * Gets all disabled plugins.
     * @return Map of disabled plugin names to Plugin objects
     */
    public Map<String, PluginLoader.Plugin> getDisabledPlugins() {
        return new HashMap<>(disabledPlugins);
    }
}