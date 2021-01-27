package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketNametagsUpdate extends LCPacket
{
    private Map<UUID, List<String>> playersMap;
    
    public Map<UUID, List<String>> getPlayersMap() {
        return this.playersMap;
    }
    
    public LCPacketNametagsUpdate() {
    }
    
    public LCPacketNametagsUpdate(final Map<UUID, List<String>> playersMap) {
        this.playersMap = playersMap;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeVarInt((this.playersMap == null) ? -1 : this.playersMap.size());
        if (this.playersMap != null) {
            final Iterator<String> iterator = null;
            AtomicReference<String> tag = new AtomicReference<>();
            this.playersMap.forEach((uuid, tags) -> {
                buf.writeUUID(uuid);
                buf.writeVarInt(tags.size());
                tags.iterator();
                while (iterator.hasNext()) {
                    tag.set(iterator.next());
                    buf.writeString(tag.get());
                }
            });
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        final int playersMapSize = buf.readVarInt();
        if (playersMapSize != -1) {
            this.playersMap = new HashMap<>();
            for (int i = 0; i < playersMapSize; ++i) {
                final UUID uuid = buf.readUUID();
                final int tagsSize = buf.readVarInt();
                final List<String> tags = new ArrayList<String>(tagsSize);
                for (int j = 0; j < tagsSize; ++j) {
                    tags.add(buf.readString());
                }
                this.playersMap.put(uuid, tags);
            }
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleNametagsUpdate(this);
    }
}
