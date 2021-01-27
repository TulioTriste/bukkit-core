package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.client.obj.ModSettings;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketModSettings extends LCPacket
{
    private ModSettings settings;
    
    public LCPacketModSettings() {
    }
    
    public LCPacketModSettings(final ModSettings modSettings) {
        this.settings = modSettings;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(ModSettings.GSON.toJson(this.settings));
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.settings = (ModSettings)ModSettings.GSON.fromJson(buf.readString(), (Class)ModSettings.class);
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleModSettings(this);
    }
    
    public ModSettings getSettings() {
        return this.settings;
    }
}
