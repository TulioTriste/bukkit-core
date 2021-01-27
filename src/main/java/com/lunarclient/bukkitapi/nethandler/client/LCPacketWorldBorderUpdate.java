package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketWorldBorderUpdate extends LCPacket
{
    private String id;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    private int durationTicks;
    
    public LCPacketWorldBorderUpdate() {
    }
    
    public LCPacketWorldBorderUpdate(final String id, final double minX, final double minZ, final double maxX, final double maxZ, final int durationTicks) {
        this.id = id;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.durationTicks = durationTicks;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.id);
        buf.buf().writeDouble(this.minX);
        buf.buf().writeDouble(this.minZ);
        buf.buf().writeDouble(this.maxX);
        buf.buf().writeDouble(this.maxZ);
        buf.buf().writeInt(this.durationTicks);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.id = buf.readString();
        this.minX = buf.buf().readDouble();
        this.minZ = buf.buf().readDouble();
        this.maxX = buf.buf().readDouble();
        this.maxZ = buf.buf().readDouble();
        this.durationTicks = buf.buf().readInt();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleWorldBorderUpdate(this);
    }
    
    public String getId() {
        return this.id;
    }
    
    public double getMinX() {
        return this.minX;
    }
    
    public double getMinZ() {
        return this.minZ;
    }
    
    public double getMaxX() {
        return this.maxX;
    }
    
    public double getMaxZ() {
        return this.maxZ;
    }
    
    public int getDurationTicks() {
        return this.durationTicks;
    }
}
