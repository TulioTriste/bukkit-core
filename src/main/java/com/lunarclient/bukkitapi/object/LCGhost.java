package com.lunarclient.bukkitapi.object;

import java.util.UUID;
import java.util.List;

public final class LCGhost
{
    private final List<UUID> ghostedPlayers;
    private final List<UUID> unGhostedPlayers;
    
    public LCGhost(final List<UUID> ghostedPlayers, final List<UUID> unGhostedPlayers) {
        this.ghostedPlayers = ghostedPlayers;
        this.unGhostedPlayers = unGhostedPlayers;
    }
    
    public List<UUID> getGhostedPlayers() {
        return this.ghostedPlayers;
    }
    
    public List<UUID> getUnGhostedPlayers() {
        return this.unGhostedPlayers;
    }
}
