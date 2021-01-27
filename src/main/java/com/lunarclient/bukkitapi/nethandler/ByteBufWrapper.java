package com.lunarclient.bukkitapi.nethandler;

import java.util.UUID;
import java.nio.charset.StandardCharsets;
import io.netty.buffer.ByteBuf;

public final class ByteBufWrapper
{
    private final ByteBuf buf;
    
    public ByteBufWrapper(final ByteBuf buf) {
        this.buf = buf;
    }
    
    public void writeVarInt(int b) {
        while ((b & 0xFFFFFF80) != 0x0) {
            this.buf.writeByte((b & 0x7F) | 0x80);
            b >>>= 7;
        }
        this.buf.writeByte(b);
    }
    
    public int readVarInt() {
        int i = 0;
        int chunk = 0;
        byte b;
        do {
            b = this.buf.readByte();
            i |= (b & 0x7F) << chunk++ * 7;
            if (chunk > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b & 0x80) == 0x80);
        return i;
    }
    
    public void writeString(final String s) {
        final byte[] arr = s.getBytes(StandardCharsets.UTF_8);
        this.writeVarInt(arr.length);
        this.buf.writeBytes(arr);
    }
    
    public String readString() {
        final int len = this.readVarInt();
        final byte[] buffer = new byte[len];
        this.buf.readBytes(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }
    
    public void writeUUID(final UUID uuid) {
        this.buf.writeLong(uuid.getMostSignificantBits());
        this.buf.writeLong(uuid.getLeastSignificantBits());
    }
    
    public UUID readUUID() {
        final long mostSigBits = this.buf.readLong();
        final long leastSigBits = this.buf.readLong();
        return new UUID(mostSigBits, leastSigBits);
    }
    
    public ByteBuf buf() {
        return this.buf;
    }
}
