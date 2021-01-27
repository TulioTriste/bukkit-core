package com.lunarclient.bukkitapi.nethandler.server;

import java.util.ArrayList;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.client.LCNetHandlerClient;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoice extends LCPacket
{
    private Set<UUID> uuids;
    private byte[] data;
    
    public LCPacketVoice(final Set<UUID> uuids, final byte[] data) {
        this.uuids = uuids;
        this.data = data;
    }
    
    public LCPacketVoice() {
    }
    
    @Override
    public void write(final ByteBufWrapper b) {
        b.writeVarInt(this.uuids.size());
        this.uuids.forEach(b::writeUUID);
        this.writeBlob(b, this.data);
    }
    
    @Override
    public void read(final ByteBufWrapper b) {
        this.uuids = new HashSet<UUID>();
        for (int size = b.readVarInt(), i = 0; i < size; ++i) {
            this.uuids.add(b.readUUID());
        }
        this.data = this.readBlob(b);
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleVoice(this);
    }
    
    @Deprecated
    public UUID getUuid() {
        return new ArrayList<UUID>(this.uuids).get(0);
    }
    
    public Set<UUID> getUuids() {
        return this.uuids;
    }
    
    public byte[] getData() {
        return this.data;
    }
}
