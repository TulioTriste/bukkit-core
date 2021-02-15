package net.pandamc.core.rank;

import org.bukkit.OfflinePlayer;

public interface RankManager {

    String getRankName(OfflinePlayer player);

    String getRankPrefix(OfflinePlayer player);

    String getRankSuffix(OfflinePlayer player);
}
