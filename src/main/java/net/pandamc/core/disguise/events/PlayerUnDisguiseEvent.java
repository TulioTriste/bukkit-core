package net.pandamc.core.disguise.events;

import net.pandamc.core.events.DisguiseEvent;
import org.bukkit.entity.Player;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:10
 */

public class PlayerUnDisguiseEvent extends DisguiseEvent {
    public PlayerUnDisguiseEvent(Player player) {
        super(player, false);
    }
}
