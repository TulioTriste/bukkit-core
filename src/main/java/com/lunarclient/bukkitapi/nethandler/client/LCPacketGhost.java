package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.ArrayList;
import java.io.IOException;
import java.util.UUID;
import java.util.List;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketGhost extends LCPacket
{
    private List<UUID> addGhostList;
    private List<UUID> removeGhostList;
    
    public LCPacketGhost() {
    }
    
    public LCPacketGhost(final List<UUID> uuidList, final List<UUID> removeGhostList) {
        this.addGhostList = uuidList;
        this.removeGhostList = removeGhostList;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(this.addGhostList.size());
        for (final UUID uuid : this.addGhostList) {
            buf.writeUUID(uuid);
        }
        buf.writeVarInt(this.removeGhostList.size());
        for (final UUID uuid : this.removeGhostList) {
            buf.writeUUID(uuid);
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        final int addListSize = buf.readVarInt();
        this.addGhostList = new ArrayList<UUID>(addListSize);
        for (int i = 0; i < addListSize; ++i) {
            this.addGhostList.add(buf.readUUID());
        }
        final int removeListSize = buf.readVarInt();
        this.removeGhostList = new ArrayList<UUID>(removeListSize);
        for (int j = 0; j < removeListSize; ++j) {
            this.removeGhostList.add(buf.readUUID());
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleGhost(this);
    }
    
    public List<UUID> getAddGhostList() {
        return this.addGhostList;
    }
    
    public List<UUID> getRemoveGhostList() {
        return this.removeGhostList;
    }
}
