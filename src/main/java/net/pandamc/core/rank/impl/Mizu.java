package net.pandamc.core.rank.impl;

import com.broustudio.MizuAPI.MizuAPI;
import net.pandamc.core.rank.RankManager;
import org.bukkit.OfflinePlayer;

public class Mizu implements RankManager {

    @Override
    public String getRankName(OfflinePlayer player) {
        return MizuAPI.getAPI().getRank(player.getUniqueId());
    }

    @Override
    public String getRankPrefix(OfflinePlayer player) {
        return MizuAPI.getAPI().getRankPrefix(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getRankSuffix(OfflinePlayer player) {
        return MizuAPI.getAPI().getRankSuffix(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }
}
