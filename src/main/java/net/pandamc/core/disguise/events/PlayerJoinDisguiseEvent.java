package net.pandamc.core.disguise.events;

import lombok.Getter;
import net.pandamc.core.disguise.PlayerDisguiseData;
import net.pandamc.core.events.DisguiseEvent;
import org.bukkit.entity.Player;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:10
 */

@Getter
public class PlayerJoinDisguiseEvent extends DisguiseEvent {

    private final PlayerDisguiseData playerDisguiseData;

    public PlayerJoinDisguiseEvent(Player player, PlayerDisguiseData disguiseData) {
        super(player, false);
        this.playerDisguiseData = disguiseData;
    }
}
