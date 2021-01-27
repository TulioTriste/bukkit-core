package com.lunarclient.bukkitapi.nethandler;

import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoice;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannel;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelRemove;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketVoiceChannelUpdate;
import com.lunarclient.bukkitapi.nethandler.shared.LCNetHandler;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketEmoteBroadcast;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketModSettings;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderUpdateNew;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderCreateNew;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketBossBar;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketGhost;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderUpdate;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorderRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketWorldBorder;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTeammates;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketStaffModState;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerUpdate;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerRule;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNotification;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsUpdate;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsOverride;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramUpdate;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologram;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketCooldown;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceMute;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceChannelSwitch;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketClientVoice;
import java.util.HashMap;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import io.netty.buffer.Unpooled;
import java.util.Map;

public abstract class LCPacket
{
    private static final Map<Class, Integer> classToId;
    private static final Map<Integer, Class> idToClass;
    private Object attachment;
    
    public static LCPacket handle(final byte[] data) {
        return handle(data, null);
    }
    
    public static LCPacket handle(final byte[] data, final Object attachment) {
        final ByteBufWrapper wrappedBuffer = new ByteBufWrapper(Unpooled.wrappedBuffer(data));
        final int packetId = wrappedBuffer.readVarInt();
        final Class packetClass = LCPacket.idToClass.get(packetId);
        if (packetClass != null) {
            try {
                final LCPacket packet = (LCPacket) packetClass.newInstance();
                packet.attach(attachment);
                packet.read(wrappedBuffer);
                return packet;
            }
            catch (IOException | InstantiationException | IllegalAccessException ex3) {
                final Exception ex2 = null;
                final Exception ex = ex2;
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    public static byte[] getPacketData(final LCPacket packet) {
        return getPacketBuf(packet).array();
    }
    
    public static ByteBuf getPacketBuf(final LCPacket packet) {
        final ByteBufWrapper wrappedBuffer = new ByteBufWrapper(Unpooled.buffer());
        wrappedBuffer.writeVarInt(LCPacket.classToId.get(packet.getClass()));
        try {
            packet.write(wrappedBuffer);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return wrappedBuffer.buf();
    }
    
    private static void addPacket(final int id, final Class clazz) {
        if (LCPacket.classToId.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class (" + clazz.getSimpleName() + "), already used by " + LCPacket.classToId.get(clazz));
        }
        if (LCPacket.idToClass.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate packet ID (" + id + "), already used by " + LCPacket.idToClass.get(id).getSimpleName());
        }
        LCPacket.classToId.put(clazz, id);
        LCPacket.idToClass.put(id, clazz);
    }
    
    public abstract void write(final ByteBufWrapper p0) throws IOException;
    
    public abstract void read(final ByteBufWrapper p0) throws IOException;
    
    public abstract void process(final LCNetHandler p0);
    
    public <T> void attach(final T obj) {
        this.attachment = obj;
    }
    
    public <T> T getAttachment() {
        return (T)this.attachment;
    }
    
    protected void writeBlob(final ByteBufWrapper b, final byte[] bytes) {
        b.buf().writeShort(bytes.length);
        b.buf().writeBytes(bytes);
    }
    
    protected byte[] readBlob(final ByteBufWrapper b) {
        final short key = b.buf().readShort();
        if (key < 0) {
            System.out.println("Key was smaller than nothing!  Weird key!");
            return null;
        }
        final byte[] blob = new byte[key];
        b.buf().readBytes(blob);
        return blob;
    }
    
    static {
        classToId = new HashMap<Class, Integer>();
        idToClass = new HashMap<Integer, Class>();
        addPacket(0, LCPacketClientVoice.class);
        addPacket(16, LCPacketVoice.class);
        addPacket(1, LCPacketVoiceChannelSwitch.class);
        addPacket(2, LCPacketVoiceMute.class);
        addPacket(17, LCPacketVoiceChannel.class);
        addPacket(18, LCPacketVoiceChannelRemove.class);
        addPacket(19, LCPacketVoiceChannelUpdate.class);
        addPacket(3, LCPacketCooldown.class);
        addPacket(4, LCPacketHologram.class);
        addPacket(6, LCPacketHologramRemove.class);
        addPacket(5, LCPacketHologramUpdate.class);
        addPacket(7, LCPacketNametagsOverride.class);
        addPacket(8, LCPacketNametagsUpdate.class);
        addPacket(9, LCPacketNotification.class);
        addPacket(10, LCPacketServerRule.class);
        addPacket(11, LCPacketServerUpdate.class);
        addPacket(12, LCPacketStaffModState.class);
        addPacket(13, LCPacketTeammates.class);
        addPacket(14, LCPacketTitle.class);
        addPacket(15, LCPacketUpdateWorld.class);
        addPacket(20, LCPacketWorldBorder.class);
        addPacket(21, LCPacketWorldBorderRemove.class);
        addPacket(22, LCPacketWorldBorderUpdate.class);
        addPacket(25, LCPacketGhost.class);
        addPacket(28, LCPacketBossBar.class);
        addPacket(29, LCPacketWorldBorderCreateNew.class);
        addPacket(30, LCPacketWorldBorderUpdateNew.class);
        addPacket(31, LCPacketModSettings.class);
        addPacket(26, LCPacketEmoteBroadcast.class);
        addPacket(23, LCPacketWaypointAdd.class);
        addPacket(24, LCPacketWaypointRemove.class);
    }
}
