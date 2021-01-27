package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketStaffModState extends LCPacket
{
    private String mod;
    private boolean state;
    
    public LCPacketStaffModState() {
    }
    
    public LCPacketStaffModState(final String mod, final boolean state) {
        this.mod = mod;
        this.state = state;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeString(this.mod);
        buf.buf().writeBoolean(this.state);
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.mod = buf.readString();
        this.state = buf.buf().readBoolean();
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleStaffModState(this);
    }
    
    public String getMod() {
        return this.mod;
    }
    
    public boolean isState() {
        return this.state;
    }
}
