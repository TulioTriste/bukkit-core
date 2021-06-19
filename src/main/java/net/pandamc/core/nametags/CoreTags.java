package net.pandamc.core.nametags;

import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CoreTags extends NametagProvider {

    public CoreTags() {
        super("YangTags", 1);
    }

    @Override
    public NametagInfo fetchNametag(Player target, Player viewer) {
        for (PotionEffect potionEffect : target.getActivePotionEffects()) {
            if (potionEffect.getType().getName().equalsIgnoreCase("INVISIBILITY")) {
                return null;
            }
        }
        return (createNametag(Core.get().getColoredRanksConfig().get("groups." + Core.get().getRankManager().getRankName(target)) != null ?
                CC.translate(Core.get().getColoredRanksConfig().getString("groups." + Core.get().getRankManager().getRankName(target)))
                : CC.translate("&r"), ""));
    }
}