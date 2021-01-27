package com.lunarclient.bukkitapi.nethandler.server;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.client.LCNetHandlerClient;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;

import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoiceChannelUpdate extends LCPacket
{
    public int status;
    private UUID channelUuid;
    private UUID uuid;
    private String name;
    
    public LCPacketVoiceChannelUpdate(final int status, final UUID channelUuid, final UUID uuid, final String name) {
        this.status = status;
        this.channelUuid = channelUuid;
        this.uuid = uuid;
        this.name = name;
    }
    
    public LCPacketVoiceChannelUpdate() {
    }
    
    @Override
    public void write(final ByteBufWrapper b) {
        b.writeVarInt(this.status);
        b.writeUUID(this.channelUuid);
        b.writeUUID(this.uuid);
        b.writeString(this.name);
    }
    
    @Override
    public void read(final ByteBufWrapper b) {
        this.status = b.readVarInt();
        this.channelUuid = b.readUUID();
        this.uuid = b.readUUID();
        this.name = b.readString();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleVoiceChannelUpdate(this);
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public UUID getChannelUuid() {
        return this.channelUuid;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
}
