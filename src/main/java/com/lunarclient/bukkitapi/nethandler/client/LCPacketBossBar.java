package com.lunarclient.bukkitapi.nethandler.client;

import com.lunarclient.bukkitapi.nethandler.ByteBufWrapper;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import java.io.IOException;

import com.lunarclient.bukkitapi.nethandler.LCPacket;

public final class LCPacketBossBar extends LCPacket
{
    private int action;
    private String text;
    private float health;
    
    public LCPacketBossBar() {
    }
    
    public LCPacketBossBar(final int action, final String text, final float health) {
        this.action = action;
        this.text = text;
        this.health = health;
    }
    
    @Override
    public void write(final ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(this.action);
        if (this.action == 0) {
            buf.writeString(this.text);
            buf.buf().writeFloat(this.health);
        }
    }
    
    @Override
    public void read(final ByteBufWrapper buf) throws IOException {
        this.action = buf.readVarInt();
        if (this.action == 0) {
            this.text = buf.readString();
            this.health = buf.buf().readFloat();
        }
    }
    
    @Override
    public void process(final LCNetHandler handler) {
        ((LCNetHandlerClient)handler).handleBossBar(this);
    }
    
    public int getAction() {
        return this.action;
    }
    
    public String getText() {
        return this.text;
    }
    
    public float getHealth() {
        return this.health;
    }
}
