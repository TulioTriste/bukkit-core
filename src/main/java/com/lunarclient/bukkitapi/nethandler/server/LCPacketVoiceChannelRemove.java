package com.lunarclient.bukkitapi.nethandler.server;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.client.LCNetHandlerClient;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;

import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoiceChannelRemove extends LCPacket
{
    private UUID uuid;
    
    public LCPacketVoiceChannelRemove(final UUID uuid) {
        this.uuid = uuid;
    }
    
    public LCPacketVoiceChannelRemove() {
    }
    
    @Override
    public void write(final ByteBufWrapper b) {
        b.writeUUID(this.uuid);
    }
    
    @Override
    public void read(final ByteBufWrapper b) {
        this.uuid = b.readUUID();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleVoiceChannelDelete(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
