package fr.neamar.kiss.pojo;

import java.util.List;

/**
 * Represents the manifest of a Viabhron plugin.
 */
public class PluginManifest {
    private String name;
    private String version;
    private List<String> permissions;
    private String description;

    public PluginManifest() {}

    public PluginManifest(String name, String version, List<String> permissions, String description) {
        this.name = name;
        this.version = version;
        this.permissions = permissions;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PluginManifest{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", permissions=" + permissions +
                ", description='" + description + '\'' +
                '}';
    }
}