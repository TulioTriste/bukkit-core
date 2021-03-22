package net.pandamc.core.disguise.nms;

import net.pandamc.core.disguise.CacheDisguiseData;
import net.pandamc.core.disguise.skin.Skin;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.entity.Player;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 05/02/2021 @ 20:07
 */

public abstract class DisguiseImplementation {

    public abstract void applyDisguise(Profile data, CacheDisguiseData cacheDisguiseData, boolean reDisguise);

    public abstract void applyUnDisguise(Profile data, CacheDisguiseData cacheDisguiseData);

    public abstract Skin getPlayerSkin(Player player);

    public static final String WEB_API = "https://sessionserver.mojang.com/session/minecraft/profile/";
    public static final String ID_API = "https://api.mojang.com/users/profiles/minecraft/";

    public void disguiseMessage(Player player, CacheDisguiseData cacheDisguiseData, boolean reDisguise) {
        player.sendMessage(CC.translate("&aYou has been disguise ass &d" + cacheDisguiseData.getDisguiseName()));
    }

    public void unDisguiseMessage(Player player) {
        player.sendMessage(CC.translate("&aYou has been undisguised."));
    }
}
