package net.pandamc.core.commands;

import net.pandamc.core.disguise.events.PlayerUnDisguiseEvent;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 17/02/2021 @ 22:51
 */

public class UnDisguiseCommand  extends Command {

    public UnDisguiseCommand() {
        super("undisguise");
        setAliases(Arrays.asList("ud","unnick"));
        setPermission("rdisguise.disguise");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        Profile profile = Profile.get(player.getUniqueId());
        if (!profile.isDisguise()) {
            player.sendMessage(CC.translate("&cAlready undisguised!"));
            return true;
        }
        new PlayerUnDisguiseEvent(player).call();
        return false;
    }
}
