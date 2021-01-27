package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketTitle extends LCPacket
{
    private String type;
    private String message;
    private float scale;
    private long displayTimeMs;
    private long fadeInTimeMs;
    private long fadeOutTimeMs;
    
    public LCPacketTitle() {
    }
    
    public LCPacketTitle(final String type, final String message, final long displayTimeMs, final long fadeInTimeMs, final long fadeOutTimeMs) {
        this(type, message, 1.0f, displayTimeMs, fadeInTimeMs, fadeOutTimeMs);
    }
    
    public LCPacketTitle(final String type, final String message, final float scale, final long displayTimeMs, final long fadeInTimeMs, final long fadeOutTimeMs) {
        this.type = type;
        this.message = message;
        this.scale = scale;
        this.displayTimeMs = displayTimeMs;
        this.fadeInTimeMs = fadeInTimeMs;
        this.fadeOutTimeMs = fadeOutTimeMs;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.type);
        buf.writeString(this.message);
        buf.buf().writeFloat(this.scale);
        buf.buf().writeLong(this.displayTimeMs);
        buf.buf().writeLong(this.fadeInTimeMs);
        buf.buf().writeLong(this.fadeOutTimeMs);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.type = buf.readString();
        this.message = buf.readString();
        this.scale = buf.buf().readFloat();
        this.displayTimeMs = buf.buf().readLong();
        this.fadeInTimeMs = buf.buf().readLong();
        this.fadeOutTimeMs = buf.buf().readLong();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleTitle(this);
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public float getScale() {
        return this.scale;
    }
    
    public long getDisplayTimeMs() {
        return this.displayTimeMs;
    }
    
    public long getFadeInTimeMs() {
        return this.fadeInTimeMs;
    }
    
    public long getFadeOutTimeMs() {
        return this.fadeOutTimeMs;
    }
}
