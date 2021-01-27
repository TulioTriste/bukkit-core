package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketUpdateWorld extends LCPacket
{
    private String world;
    
    public LCPacketUpdateWorld() {
    }
    
    public LCPacketUpdateWorld(final String world) {
        this.world = world;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.world);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.world = buf.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleUpdateWorld(this);
    }
    
    public String getWorld() {
        return this.world;
    }
}
