package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketServerUpdate extends LCPacket
{
    private String server;
    
    public LCPacketServerUpdate() {
    }
    
    public LCPacketServerUpdate(final String server) {
        this.server = server;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.server);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.server = buf.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleServerUpdate(this);
    }
    
    public String getServer() {
        return this.server;
    }
}
