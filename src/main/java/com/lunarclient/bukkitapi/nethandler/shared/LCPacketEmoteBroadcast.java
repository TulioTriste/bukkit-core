package com.lunarclient.bukkitapi.nethandler.shared;

import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;

import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketEmoteBroadcast extends LCPacket
{
    private UUID uuid;
    private int emoteId;
    
    public LCPacketEmoteBroadcast() {
    }
    
    public LCPacketEmoteBroadcast(final UUID uuid, final int emoteId) {
        this.uuid = uuid;
        this.emoteId = emoteId;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.buf().writeInt(this.emoteId);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        this.emoteId = buf.buf().readInt();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        handler.handleEmote(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public int getEmoteId() {
        return this.emoteId;
    }
}
