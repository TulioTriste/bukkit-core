package net.pandamc.core.rank;

import lombok.Getter;
import lombok.Setter;
import net.pandamc.core.Core;
import net.pandamc.core.rank.impl.*;
import org.bukkit.Bukkit;

@Getter
@Setter
public class Rank {

    public static void init() {
        if (Bukkit.getPluginManager().getPlugin("AquaCore") != null) {
            Core.get().setRankManager(new AquaCore());
            Core.get().setRankSystem("AquaCore");
        }
        else if (Bukkit.getPluginManager().getPlugin("HestiaCore") != null) {
            Core.get().setRankManager(new HestiaCore());
            Core.get().setRankSystem("HestiaCore");
        }
//        else if (Bukkit.getPluginManager().getPlugin("Zoom") != null) {
//            Core.get().setRankManager(new Zoom());
//            Core.get().setRankSystem("Zoom");
//        }
        else if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            Core.get().loadVault();
            Core.get().setRankManager(new Vault());
            if (Core.get().getChat().getName().contains("PermissionsEx")) {
                Core.get().setRankSystem("PermissionsEx");
            }
            else if (Core.get().getChat().getName().contains("LuckPerms")) {
                Core.get().setRankSystem("LuckPerms");
            }
            else {
                Core.get().setRankManager(new Default());
                Core.get().setRankSystem("Unknown");
            }
        }
        else {
            Core.get().setRankManager(new Default());
            Core.get().setRankSystem("None");
        }
    }
}
