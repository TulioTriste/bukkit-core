package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketNotification extends LCPacket
{
    private String message;
    private long durationMs;
    private String level;
    
    public LCPacketNotification() {
    }
    
    public LCPacketNotification(final String message, final long durationMs, final String level) {
        this.message = message;
        this.durationMs = durationMs;
        this.level = level;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.message);
        buf.buf().writeLong(this.durationMs);
        buf.writeString(this.level);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.message = buf.readString();
        this.durationMs = buf.buf().readLong();
        this.level = buf.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleNotification(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public long getDurationMs() {
        return this.durationMs;
    }
    
    public String getLevel() {
        return this.level;
    }
}
