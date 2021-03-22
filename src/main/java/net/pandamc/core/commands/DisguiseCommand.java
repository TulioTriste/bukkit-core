package net.pandamc.core.commands;

import net.pandamc.core.disguise.events.PlayerDisguiseEvent;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:15
 */

public class DisguiseCommand extends Command {

    public DisguiseCommand() {
        super("disguise");
        setAliases(Arrays.asList("d", "nick"));
        setPermission("bukkit.core.disguise");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        Profile profile = Profile.get(player.getUniqueId());
        if (profile.isDisguise()) {
            player.sendMessage(CC.translate("&cAlready disguised."));
            return true;
        }
        new PlayerDisguiseEvent(player).call();
        return false;
    }
}
