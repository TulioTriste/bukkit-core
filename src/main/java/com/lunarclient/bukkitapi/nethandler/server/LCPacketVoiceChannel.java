package com.lunarclient.bukkitapi.nethandler.server;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.client.LCNetHandlerClient;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketVoiceChannel extends LCPacket
{
    private UUID uuid;
    private String name;
    private Map<UUID, String> players;
    private Map<UUID, String> listening;
    
    public LCPacketVoiceChannel(final UUID uuid, final String name, final Map<UUID, String> players, final Map<UUID, String> listening) {
        this.uuid = uuid;
        this.name = name;
        this.players = players;
        this.listening = listening;
    }
    
    public LCPacketVoiceChannel() {
    }
    
    @Override
    public void write(final ByteBufWrapper b) {
        b.writeUUID(this.uuid);
        b.writeString(this.name);
        this.writeMap(b, this.players);
        this.writeMap(b, this.listening);
    }
    
    @Override
    public void read(final ByteBufWrapper b) {
        this.uuid = b.readUUID();
        this.name = b.readString();
        this.players = this.readMap(b);
        this.listening = this.readMap(b);
    }
    
    private void writeMap(final ByteBufWrapper b, final Map<UUID, String> players) {
        b.writeVarInt(players.size());
        for (final Map.Entry<UUID, String> player : players.entrySet()) {
            b.writeUUID(player.getKey());
            b.writeString(player.getValue());
        }
    }
    
    private Map<UUID, String> readMap(final ByteBufWrapper b) {
        final int size = b.readVarInt();
        final Map<UUID, String> players = new HashMap<UUID, String>();
        for (int i = 0; i < size; ++i) {
            final UUID uuid = b.readUUID();
            final String name = b.readString();
            players.put(uuid, name);
        }
        return players;
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleVoiceChannels(this);
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Map<UUID, String> getPlayers() {
        return this.players;
    }
    
    public Map<UUID, String> getListening() {
        return this.listening;
    }
}
