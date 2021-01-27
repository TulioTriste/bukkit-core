package com.lunarclient.bukkitapi.nethandler.server;

import com.lunarclient.bukkitapi.nethandler.client.LCPacketClientVoice;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceChannelSwitch;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceMute;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;

public interface LCNetHandlerServer extends LCNetHandler
{
    void handleStaffModStatus(final LCPacketStaffModStatus p0);
    
    void handleVoice(final LCPacketClientVoice p0);
    
    void handleVoiceMute(final LCPacketVoiceMute p0);
    
    void handleVoiceChannelSwitch(final LCPacketVoiceChannelSwitch p0);
}
