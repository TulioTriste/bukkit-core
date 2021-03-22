package net.pandamc.core.disguise;

import org.bukkit.entity.Player;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 19/02/2021 @ 00:10
 */

public abstract class DisguiseAction {

    public abstract void afterDisguise(Player player);

    public abstract void afterUnDisguise(Player player);
}
