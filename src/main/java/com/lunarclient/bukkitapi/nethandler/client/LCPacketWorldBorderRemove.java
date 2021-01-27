package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketWorldBorderRemove extends LCPacket
{
    private String id;
    
    public LCPacketWorldBorderRemove() {
    }
    
    public LCPacketWorldBorderRemove(final String id) {
        this.id = id;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.id);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.id = buf.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleWorldBorderRemove(this);
    }
    
    public String getId() {
        return this.id;
    }
}
