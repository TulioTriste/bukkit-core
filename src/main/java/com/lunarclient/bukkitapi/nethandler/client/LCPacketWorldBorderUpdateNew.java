package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public class LCPacketWorldBorderUpdateNew extends LCPacket
{
    private String id;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    private int durationTicks;
    private boolean cancelsEntry;
    private boolean cancelsExit;
    private int color;
    
    public LCPacketWorldBorderUpdateNew() {
    }
    
    public LCPacketWorldBorderUpdateNew(final String id, final double minX, final double minZ, final double maxX, final double maxZ, final int durationTicks, final boolean cancelsEntry, final boolean cancelsExit, final int color) {
        this.id = id;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.durationTicks = durationTicks;
        this.cancelsEntry = cancelsEntry;
        this.cancelsExit = cancelsExit;
        this.color = color;
    }
    
    @Override
    public void write(final ByteBufWrapper b) throws IOException {
        b.writeString(this.id);
        b.buf().writeDouble(this.minX);
        b.buf().writeDouble(this.minZ);
        b.buf().writeDouble(this.maxX);
        b.buf().writeDouble(this.maxZ);
        b.buf().writeInt(this.durationTicks);
        b.buf().writeBoolean(this.cancelsEntry);
        b.buf().writeBoolean(this.cancelsExit);
        b.buf().writeInt(this.color);
    }
    
    @Override
    public void read(final ByteBufWrapper b) throws IOException {
        this.id = b.readString();
        this.minX = b.buf().readDouble();
        this.minZ = b.buf().readDouble();
        this.maxX = b.buf().readDouble();
        this.maxZ = b.buf().readDouble();
        this.durationTicks = b.buf().readInt();
        this.cancelsEntry = b.buf().readBoolean();
        this.cancelsExit = b.buf().readBoolean();
        this.color = b.buf().readInt();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleWorldBorderUpdateNew(this);
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
    
    public boolean isCancelsEntry() {
        return this.cancelsEntry;
    }
    
    public boolean isCancelsExit() {
        return this.cancelsExit;
    }
    
    public int getColor() {
        return this.color;
    }
}
