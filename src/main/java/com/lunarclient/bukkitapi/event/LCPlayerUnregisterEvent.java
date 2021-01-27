package com.lunarclient.bukkitapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class LCPlayerUnregisterEvent extends PlayerEvent
{
    private static HandlerList handlerList;
    
    public LCPlayerUnregisterEvent(final Player player) {
        super(player);
    }
    
    public static HandlerList getHandlerList() {
        return LCPlayerUnregisterEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return LCPlayerUnregisterEvent.handlerList;
    }
    
    static {
        LCPlayerUnregisterEvent.handlerList = new HandlerList();
    }
}
