package com.lunarclient.bukkitapi.nethandler.shared;

public interface LCNetHandler
{
    void handleAddWaypoint(final LCPacketWaypointAdd p0);
    
    void handleRemoveWaypoint(final LCPacketWaypointRemove p0);
    
    void handleEmote(final LCPacketEmoteBroadcast p0);
}
