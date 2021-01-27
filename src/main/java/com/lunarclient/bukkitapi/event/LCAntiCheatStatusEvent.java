package com.lunarclient.bukkitapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class LCAntiCheatStatusEvent extends PlayerEvent
{
    private static final HandlerList handlerList;
    private final Status status;
    
    public LCAntiCheatStatusEvent(final Player who, final Status status) {
        super(who);
        this.status = status;
    }
    
    public static HandlerList getHandlerList() {
        return LCAntiCheatStatusEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return LCAntiCheatStatusEvent.handlerList;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    static {
        handlerList = new HandlerList();
    }
    
    public enum Status
    {
        PROTECTED, 
        UNPROTECTED
    }
}
