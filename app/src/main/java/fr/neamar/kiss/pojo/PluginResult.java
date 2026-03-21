package fr.neamar.kiss.pojo;

import fr.neamar.kiss.result.Result;

public class PluginResult extends Result {
    private String pluginId;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }
}
