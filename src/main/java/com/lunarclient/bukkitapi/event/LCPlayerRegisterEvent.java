package com.lunarclient.bukkitapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class LCPlayerRegisterEvent extends PlayerEvent
{
    private static HandlerList handlerList;
    
    public LCPlayerRegisterEvent(final Player player) {
        super(player);
    }
    
    public static HandlerList getHandlerList() {
        return LCPlayerRegisterEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return LCPlayerRegisterEvent.handlerList;
    }
    
    static {
        LCPlayerRegisterEvent.handlerList = new HandlerList();
    }
}
