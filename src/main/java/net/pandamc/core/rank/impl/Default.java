package net.pandamc.core.rank.impl;

import net.pandamc.core.rank.RankManager;
import org.bukkit.OfflinePlayer;

public class Default implements RankManager {

    @Override
    public String getRankName(OfflinePlayer player) {
        return "Default";
    }

    @Override
    public String getRankPrefix(OfflinePlayer player) {
        return "Default";
    }

    @Override
    public String getRankSuffix(OfflinePlayer player) {
        return "Default";
    }
}
