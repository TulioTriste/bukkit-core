package com.lunarclient.bukkitapi.nethandler.client.obj;

import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

public final class ModSettings
{
    public static final Gson GSON;
    private final Map<String, ModSetting> modSettings;
    
    public ModSettings() {
        this.modSettings = new HashMap<String, ModSetting>();
    }
    
    public ModSettings addModSetting(final String modId, final ModSetting setting) {
        this.modSettings.put(modId, setting);
        return this;
    }
    
    public ModSetting getModSetting(final String modId) {
        return this.modSettings.get(modId);
    }
    
    public Map<String, ModSetting> getModSettings() {
        return this.modSettings;
    }
    
    static {
        GSON = new Gson();
    }
    
    public static class ModSetting
    {
        private boolean enabled;
        private Map<String, Object> properties;
        
        public ModSetting() {
        }
        
        public ModSetting(final boolean enabled, final Map<String, Object> properties) {
            this.enabled = enabled;
            this.properties = properties;
        }
        
        public boolean isEnabled() {
            return this.enabled;
        }
        
        public Map<String, Object> getProperties() {
            return this.properties;
        }
        
        @Override
        public String toString() {
            return "ModSetting{enabled=" + this.enabled + ", properties=" + this.properties + '}';
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final ModSetting that = (ModSetting)o;
            return this.enabled == that.enabled && Objects.equals(this.properties, that.properties);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.enabled, this.properties);
        }
    }
}
