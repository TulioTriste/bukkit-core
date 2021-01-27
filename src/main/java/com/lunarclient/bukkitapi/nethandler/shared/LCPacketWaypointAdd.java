package com.lunarclient.bukkitapi.nethandler.shared;

import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketWaypointAdd extends LCPacket
{
    private String name;
    private String world;
    private int color;
    private int x;
    private int y;
    private int z;
    private boolean forced;
    private boolean visible;
    
    public LCPacketWaypointAdd() {
    }
    
    public LCPacketWaypointAdd(final String name, final String world, final int color, final int x, final int y, final int z, final boolean forced, final boolean visible) {
        this.name = name;
        this.world = world;
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
        this.forced = forced;
        this.visible = visible;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.name);
        buf.writeString(this.world);
        buf.buf().writeInt(this.color);
        buf.buf().writeInt(this.x);
        buf.buf().writeInt(this.y);
        buf.buf().writeInt(this.z);
        buf.buf().writeBoolean(this.forced);
        buf.buf().writeBoolean(this.visible);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.name = buf.readString();
        this.world = buf.readString();
        this.color = buf.buf().readInt();
        this.x = buf.buf().readInt();
        this.y = buf.buf().readInt();
        this.z = buf.buf().readInt();
        this.forced = buf.buf().readBoolean();
        this.visible = buf.buf().readBoolean();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        handler.handleAddWaypoint(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public boolean isForced() {
        return this.forced;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
}
