package com.lunarclient.bukkitapi.nethandler.client.obj;

public enum ServerRule
{
    MINIMAP_STATUS("minimapStatus", String.class),
    SERVER_HANDLES_WAYPOINTS("serverHandlesWaypoints", Boolean.class),
    COMPETITIVE_GAME("competitiveGame", Boolean.class),
    SHADERS_DISABLED("shadersDisabled", Boolean.class),
    LEGACY_ENCHANTING("legacyEnchanting", Boolean.class),
    VOICE_ENABLED("voiceEnabled", Boolean.class);
    
    private final String id;
    private final Class type;
    
    ServerRule(final String id, final Class type) {
        this.id = id;
        this.type = type;
    }
    
    public static ServerRule getRule(final String id) {
        for (final ServerRule existing : values()) {
            if (existing.id.equals(id)) {
                return existing;
            }
        }
        return null;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Class getType() {
        return this.type;
    }
}
