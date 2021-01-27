package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketWorldBorder extends LCPacket
{
    private String id;
    private String world;
    private boolean cancelsExit;
    private boolean canShrinkExpand;
    private int color;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    
    public LCPacketWorldBorder() {
        this.color = -13421569;
    }
    
    public LCPacketWorldBorder(final String id, final String world, final boolean cancelsExit, final boolean canShrinkExpand, final int color, final double minX, final double minZ, final double maxX, final double maxZ) {
        this.color = -13421569;
        this.id = id;
        this.world = world;
        this.cancelsExit = cancelsExit;
        this.canShrinkExpand = canShrinkExpand;
        this.color = color;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.buf().writeBoolean(this.id != null);
        if (this.id != null) {
            buf.writeString(this.id);
        }
        buf.writeString(this.world);
        buf.buf().writeBoolean(this.cancelsExit);
        buf.buf().writeBoolean(this.canShrinkExpand);
        buf.buf().writeInt(this.color);
        buf.buf().writeDouble(this.minX);
        buf.buf().writeDouble(this.minZ);
        buf.buf().writeDouble(this.maxX);
        buf.buf().writeDouble(this.maxZ);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        if (buf.buf().readBoolean()) {
            this.id = buf.readString();
        }
        this.world = buf.readString();
        this.cancelsExit = buf.buf().readBoolean();
        this.canShrinkExpand = buf.buf().readBoolean();
        this.color = buf.buf().readInt();
        this.minX = buf.buf().readDouble();
        this.minZ = buf.buf().readDouble();
        this.maxX = buf.buf().readDouble();
        this.maxZ = buf.buf().readDouble();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleWorldBorder(this);
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public boolean isCancelsExit() {
        return this.cancelsExit;
    }
    
    public boolean isCanShrinkExpand() {
        return this.canShrinkExpand;
    }
    
    public int getColor() {
        return this.color;
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
}
