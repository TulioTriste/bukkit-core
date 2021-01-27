package com.lunarclient.bukkitapi.nethandler.server;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketStaffModStatus extends LCPacket
{
    private final Set<String> enabled;
    
    public LCPacketStaffModStatus() {
        this.enabled = new HashSet<String>();
    }
    
    public LCPacketStaffModStatus(final Set<String> enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(this.enabled.size());
        for (final String mod : this.enabled) {
            buf.writeString(mod);
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        for (int size = buf.readVarInt(), i = 0; i < size; ++i) {
            this.enabled.add(buf.readString());
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerServer)handler).handleStaffModStatus(this);
    }
    
    public Set<String> getEnabled() {
        return this.enabled;
    }
}
