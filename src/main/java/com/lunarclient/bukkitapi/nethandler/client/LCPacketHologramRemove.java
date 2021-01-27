package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketHologramRemove extends LCPacket
{
    private UUID uuid;
    
    public LCPacketHologramRemove() {
    }
    
    public LCPacketHologramRemove(final UUID uuid) {
        this.uuid = uuid;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleRemoveHologram(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
