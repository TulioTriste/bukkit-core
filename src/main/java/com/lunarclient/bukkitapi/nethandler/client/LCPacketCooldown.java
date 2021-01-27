package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketCooldown extends LCPacket
{
    private String message;
    private long durationMs;
    private int iconId;
    
    public LCPacketCooldown() {
    }
    
    public LCPacketCooldown(final String message, final long durationMs, final int iconId) {
        this.message = message;
        this.durationMs = durationMs;
        this.iconId = iconId;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.message);
        buf.buf().writeLong(this.durationMs);
        buf.buf().writeInt(this.iconId);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.message = buf.readString();
        this.durationMs = buf.buf().readLong();
        this.iconId = buf.buf().readInt();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleCooldown(this);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public long getDurationMs() {
        return this.durationMs;
    }
    
    public int getIconId() {
        return this.iconId;
    }
}
