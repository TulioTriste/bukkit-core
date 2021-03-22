package net.pandamc.core.disguise.events;

import net.pandamc.core.events.DisguiseEvent;
import org.bukkit.entity.Player;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:09
 */

public class PlayerDisguiseEvent extends DisguiseEvent {
    public PlayerDisguiseEvent(Player player) {
        super(player, false);
    }
}
