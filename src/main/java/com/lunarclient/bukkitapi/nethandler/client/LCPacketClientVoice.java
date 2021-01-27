package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.server.LCNetHandlerServer;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketClientVoice extends LCPacket
{
    private byte[] data;
    
    public LCPacketClientVoice() {
    }
    
    public LCPacketClientVoice(final byte[] data) {
        this.data = data;
    }
    
    @Override
    public void write(final ByteBufWrapper b) throws IOException {
        this.writeBlob(b, this.data);
    }
    
    @Override
    public void read(final ByteBufWrapper b) throws IOException {
        this.data = this.readBlob(b);
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerServer)handler).handleVoice(this);
    }
    
    public byte[] getData() {
        return this.data;
    }
}
