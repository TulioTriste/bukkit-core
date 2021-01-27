package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketHologramUpdate extends LCPacket
{
    private UUID uuid;
    private List<String> lines;
    
    public LCPacketHologramUpdate() {
    }
    
    public LCPacketHologramUpdate(final UUID uuid, final List<String> lines) {
        this.uuid = uuid;
        this.lines = lines;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.writeVarInt(this.lines.size());
        for (final String s : this.lines) {
            buf.writeString(s);
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        final int linesSize = buf.readVarInt();
        this.lines = new ArrayList<String>(linesSize);
        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(buf.readString());
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleUpdateHologram(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public List<String> getLines() {
        return this.lines;
    }
}
