package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.server.LCNetHandlerServer;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoiceMute extends LCPacket
{
    private UUID muting;
    private int volume;
    
    public LCPacketVoiceMute() {
    }
    
    public LCPacketVoiceMute(final UUID muting) {
        this(muting, 0);
    }
    
    public LCPacketVoiceMute(final UUID muting, final int volume) {
        this.muting = muting;
        this.volume = volume;
    }
    
    @Override
    public void write(final ByteBufWrapper b) throws IOException {
        b.writeUUID(this.muting);
        b.writeVarInt(this.volume);
    }
    
    @Override
    public void read(final ByteBufWrapper b) throws IOException {
        this.muting = b.readUUID();
        this.volume = b.readVarInt();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerServer)handler).handleVoiceMute(this);
    }
    
    public UUID getMuting() {
        return this.muting;
    }
    
    public int getVolume() {
        return this.volume;
    }
}
