package fr.neamar.kiss.loader;

import fr.neamar.kiss.pojo.PluginManifest;
import fr.neamar.kiss.pojo.PluginCertificate;
import fr.neamar.kiss.pojo.PluginResult;
import fr.neamar.kiss.result.Result;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads and verifies Viabhron plugins from the filesystem.
 */
public class PluginLoader {

    /**
     * Loads all valid plugins from the specified directory.
     * @param pluginDir Path to the directory containing plugins
     * @return List of valid Plugin objects
     */
    public static List<Plugin> loadPlugins(String pluginDir) {
        List<Plugin> plugins = new ArrayList<>();
        File dir = new File(pluginDir);

        if (!dir.exists() || !dir.isDirectory()) {
            return plugins;
        }

        File[] pluginFiles = dir.listFiles((d, name) -> name.endsWith(".vfplugin"));
        if (pluginFiles == null) {
            return plugins;
        }

        for (File pluginFile : pluginFiles) {
            try {
                Plugin plugin = loadPlugin(pluginFile.getAbsolutePath());
                if (plugin != null) {
                    plugins.add(plugin);
                }
            } catch (Exception e) {
                // Log error and continue
            }
        }

        return plugins;
    }

    /**
     * Loads and verifies a single plugin.
     * @param pluginPath Path to the plugin directory
     * @return Plugin object if valid, null otherwise
     */
    private static Plugin loadPlugin(String pluginPath) {
        try {
            File manifestFile = new File(pluginPath + "/manifest.json");
            File certificateFile = new File(pluginPath + "/certificate.json");

            if (!manifestFile.exists() || !certificateFile.exists()) {
                return null;
            }

            // Load manifest
            JSONObject manifestJson = new JSONObject(new JSONTokener(new FileReader(manifestFile)));
            PluginManifest manifest = new PluginManifest(
                manifestJson.optString("name"),
                manifestJson.optString("version"),
                toStringList(manifestJson.optJSONArray("permissions")),
                manifestJson.optString("description")
            );

            // Load certificate
            JSONObject certificateJson = new JSONObject(new JSONTokener(new FileReader(certificateFile)));
            PluginCertificate certificate = new PluginCertificate(
                certificateJson.optString("issuer"),
                certificateJson.optString("signature")
            );

            // Validate certificate
            if (!certificate.validate()) {
                return null;
            }

            // Load plugin.js (placeholder)
            File pluginJsFile = new File(pluginPath + "/plugin.js");
            if (!pluginJsFile.exists()) {
                return null;
            }

            return new Plugin(manifest, certificate, pluginJsFile.getAbsolutePath());

        } catch (Exception e) {
            return null;
        }
    }

    private static List<String> toStringList(Object jsonArray) {
        List<String> list = new ArrayList<>();
        if (jsonArray instanceof org.json.JSONArray) {
            org.json.JSONArray arr = (org.json.JSONArray) jsonArray;
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.optString(i));
            }
        }
        return list;
    }

    /**
     * Represents a loaded plugin.
     */
    public static class Plugin {
        private final PluginManifest manifest;
        private final PluginCertificate certificate;
        private final String pluginJsPath;

        public Plugin(PluginManifest manifest, PluginCertificate certificate, String pluginJsPath) {
            this.manifest = manifest;
            this.certificate = certificate;
            this.pluginJsPath = pluginJsPath;
        }

        public PluginManifest getManifest() {
            return manifest;
        }

        public PluginCertificate getCertificate() {
            return certificate;
        }

        public String getPluginJsPath() {
            return pluginJsPath;
        }
    }
}