package net.pandamc.core.rank.impl;

import net.pandamc.core.Core;
import net.pandamc.core.rank.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Vault implements RankManager {

    @Override
    public String getRankName(OfflinePlayer player) {
        return Core.get().getChat().getPrimaryGroup(Bukkit.getWorlds().get(0).getName(), player);
    }

    @Override
    public String getRankPrefix(OfflinePlayer player) {
        return Core.get().getChat().getPlayerPrefix(Bukkit.getWorlds().get(0).getName(), player);
    }

    @Override
    public String getRankSuffix(OfflinePlayer player) {
        return Core.get().getChat().getPlayerSuffix(Bukkit.getWorlds().get(0).getName(), player);
    }
}
