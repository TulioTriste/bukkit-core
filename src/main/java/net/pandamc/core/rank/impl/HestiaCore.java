package net.pandamc.core.rank.impl;

import me.quartz.hestia.HestiaAPI;
import net.pandamc.core.rank.RankManager;
import org.bukkit.OfflinePlayer;

public class HestiaCore implements RankManager {

    @Override
    public String getRankName(OfflinePlayer player) {
        return HestiaAPI.instance.getRank(player.getUniqueId());
    }

    @Override
    public String getRankPrefix(OfflinePlayer player) {
        return HestiaAPI.instance.getRankPrefix(player.getUniqueId());
    }

    @Override
    public String getRankSuffix(OfflinePlayer player) {
        return HestiaAPI.instance.getRankSuffix(player.getUniqueId());
    }
}
