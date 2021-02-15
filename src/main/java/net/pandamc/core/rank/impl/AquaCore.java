package net.pandamc.core.rank.impl;

import me.activated.core.api.player.PlayerData;
import me.activated.core.plugin.AquaCoreAPI;
import net.pandamc.core.rank.RankManager;
import org.bukkit.OfflinePlayer;

public class AquaCore implements RankManager {

    @Override
    public String getRankName(OfflinePlayer player) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId());
        return (data == null) ? "No Data" : data.getHighestRank().getName();
    }

    @Override
    public String getRankPrefix(OfflinePlayer player) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId());
        return (data == null) ? "No Data" : data.getHighestRank().getPrefix();
    }

    @Override
    public String getRankSuffix(OfflinePlayer player) {
        PlayerData data = AquaCoreAPI.INSTANCE.getPlayerData(player.getUniqueId());
        return (data == null) ? "No Data" : data.getHighestRank().getSuffix();
    }
}
