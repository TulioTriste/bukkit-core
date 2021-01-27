package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketHologram extends LCPacket
{
    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private List<String> lines;
    
    public LCPacketHologram() {
    }
    
    public LCPacketHologram(final UUID uuid, final double x, final double y, final double z, final List<String> lines) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lines = lines;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.buf().writeDouble(this.x);
        buf.buf().writeDouble(this.y);
        buf.buf().writeDouble(this.z);
        buf.writeVarInt(this.lines.size());
        for (final String s : this.lines) {
            buf.writeString(s);
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        this.x = buf.buf().readDouble();
        this.y = buf.buf().readDouble();
        this.z = buf.buf().readDouble();
        final int linesSize = buf.readVarInt();
        this.lines = new ArrayList<String>(linesSize);
        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(buf.readString());
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleAddHologram(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public List<String> getLines() {
        return this.lines;
    }
}
