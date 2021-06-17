package net.pandamc.core.nametags;

import lombok.Getter;
import net.pandamc.yang.nametags.packets.ScoreboardTeamPacketMod;

import java.util.ArrayList;

@Getter
public class NametagInfo {

    private String name;
    private String prefix;
    private String suffix;
    private ScoreboardTeamPacketMod teamAddPacket;

    protected NametagInfo(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;

        teamAddPacket = new ScoreboardTeamPacketMod(name, prefix, suffix, new ArrayList<String>(), 0);
    }
}