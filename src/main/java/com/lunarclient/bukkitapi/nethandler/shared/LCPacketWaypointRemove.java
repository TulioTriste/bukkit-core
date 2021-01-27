package com.lunarclient.bukkitapi.nethandler.shared;

import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketWaypointRemove extends LCPacket
{
    private String name;
    private String world;
    
    public LCPacketWaypointRemove() {
    }
    
    public LCPacketWaypointRemove(final String name, final String world) {
        this.name = name;
        this.world = world;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.name);
        buf.writeString(this.world);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.name = buf.readString();
        this.world = buf.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        handler.handleRemoveWaypoint(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getWorld() {
        return this.world;
    }
}
