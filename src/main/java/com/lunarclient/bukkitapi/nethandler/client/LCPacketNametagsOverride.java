package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketNametagsOverride extends LCPacket
{
    private UUID player;
    private List<String> tags;
    
    public LCPacketNametagsOverride() {
    }
    
    public LCPacketNametagsOverride(final UUID player, final List<String> tags) {
        this.player = player;
        this.tags = tags;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.player);
        buf.buf().writeBoolean(this.tags != null);
        if (this.tags != null) {
            buf.writeVarInt(this.tags.size());
            for (final String tag : this.tags) {
                buf.writeString(tag);
            }
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.player = buf.readUUID();
        if (buf.buf().readBoolean()) {
            final int tagsSize = buf.readVarInt();
            this.tags = new ArrayList<String>(tagsSize);
            for (int i = 0; i < tagsSize; ++i) {
                this.tags.add(buf.readString());
            }
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleOverrideNametags(this);
    }
    
    public UUID getPlayer() {
        return this.player;
    }
    
    public List<String> getTags() {
        return this.tags;
    }
}
