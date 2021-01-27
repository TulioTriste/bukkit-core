package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelRemove;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelUpdate;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannel;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoice;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;

public interface LCNetHandlerClient extends LCNetHandler
{
    void handleBossBar(final LCPacketBossBar p0);
    
    void handleCooldown(final LCPacketCooldown p0);
    
    void handleGhost(final LCPacketGhost p0);
    
    void handleAddHologram(final LCPacketHologram p0);
    
    void handleRemoveHologram(final LCPacketHologramRemove p0);
    
    void handleUpdateHologram(final LCPacketHologramUpdate p0);
    
    void handleOverrideNametags(final LCPacketNametagsOverride p0);
    
    void handleNametagsUpdate(final LCPacketNametagsUpdate p0);
    
    void handleNotification(final LCPacketNotification p0);
    
    void handleServerRule(final LCPacketServerRule p0);
    
    void handleServerUpdate(final LCPacketServerUpdate p0);
    
    void handleStaffModState(final LCPacketStaffModState p0);
    
    void handleTeammates(final LCPacketTeammates p0);
    
    void handleTitle(final LCPacketTitle p0);
    
    void handleUpdateWorld(final LCPacketUpdateWorld p0);
    
    void handleWorldBorder(final LCPacketWorldBorder p0);
    
    void handleWorldBorderCreateNew(final LCPacketWorldBorderCreateNew p0);
    
    void handleWorldBorderRemove(final LCPacketWorldBorderRemove p0);
    
    void handleWorldBorderUpdate(final LCPacketWorldBorderUpdate p0);
    
    void handleWorldBorderUpdateNew(final LCPacketWorldBorderUpdateNew p0);
    
    void handleVoice(final LCPacketVoice p0);
    
    void handleVoiceChannels(final LCPacketVoiceChannel p0);
    
    void handleVoiceChannelUpdate(final LCPacketVoiceChannelUpdate p0);
    
    void handleVoiceChannelDelete(final LCPacketVoiceChannelRemove p0);
    
    void handleModSettings(final LCPacketModSettings p0);
}
