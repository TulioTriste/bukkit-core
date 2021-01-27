package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketTeammates extends LCPacket
{
    private UUID leader;
    private long lastMs;
    private Map<UUID, Map<String, Double>> players;
    
    public LCPacketTeammates() {
    }
    
    public LCPacketTeammates(final UUID leader, final long lastMs, final Map<UUID, Map<String, Double>> players) {
        this.leader = leader;
        this.lastMs = lastMs;
        this.players = players;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.buf().writeBoolean(this.leader != null);
        if (this.leader != null) {
            buf.writeUUID(this.leader);
        }
        buf.buf().writeLong(this.lastMs);
        buf.writeVarInt(this.players.size());
        this.players.forEach((uuid, posMap) -> {
            buf.writeUUID(uuid);
            buf.writeVarInt(posMap.size());
            posMap.forEach((key, val) -> {
                buf.writeString(key);
                buf.buf().writeDouble(val);
            });
        });
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        if (buf.buf().readBoolean()) {
            this.leader = buf.readUUID();
        }
        this.lastMs = buf.buf().readLong();
        final int playersSize = buf.readVarInt();
        this.players = new HashMap<UUID, Map<String, Double>>();
        for (int i = 0; i < playersSize; ++i) {
            final UUID uuid = buf.readUUID();
            final int posMapSize = buf.readVarInt();
            final Map<String, Double> posMap = new HashMap<String, Double>();
            for (int j = 0; j < posMapSize; ++j) {
                final String key = buf.readString();
                final double val = buf.buf().readDouble();
                posMap.put(key, val);
            }
            this.players.put(uuid, posMap);
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleTeammates(this);
    }
    
    public UUID getLeader() {
        return this.leader;
    }
    
    public long getLastMs() {
        return this.lastMs;
    }
    
    public Map<UUID, Map<String, Double>> getPlayers() {
        return this.players;
    }
}
