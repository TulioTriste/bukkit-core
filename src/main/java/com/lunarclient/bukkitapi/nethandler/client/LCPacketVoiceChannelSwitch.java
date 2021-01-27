package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.server.LCNetHandlerServer;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoiceChannelSwitch extends LCPacket
{
    private UUID switchingTo;
    
    public LCPacketVoiceChannelSwitch() {
    }
    
    public LCPacketVoiceChannelSwitch(final UUID switchingTo) {
        this.switchingTo = switchingTo;
    }
    
    @Override
    public void write(final ByteBufWrapper b) throws IOException {
        b.writeUUID(this.switchingTo);
    }
    
    @Override
    public void read(final ByteBufWrapper b) throws IOException {
        this.switchingTo = b.readUUID();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerServer)handler).handleVoiceChannelSwitch(this);
    }
    
    public UUID getSwitchingTo() {
        return this.switchingTo;
    }
}
