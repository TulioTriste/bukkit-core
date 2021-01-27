package com.lunarclient.bukkitapi.event;

import org.bukkit.entity.Player;
import com.lunarclient.bukkitapi.nethandler.LCPacket;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public final class LCPacketSentEvent extends PlayerEvent
{
    private static HandlerList handlerList;
    private final LCPacket packet;
    
    public LCPacketSentEvent(final Player player, final LCPacket packet) {
        super(player);
        this.packet = packet;
    }
    
    public static HandlerList getHandlerList() {
        return LCPacketSentEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return LCPacketSentEvent.handlerList;
    }
    
    public LCPacket getPacket() {
        return this.packet;
    }
    
    static {
        LCPacketSentEvent.handlerList = new HandlerList();
    }
}
