package net.pandamc.core.commands;

import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:14
 */

public class CheckDisguiseCommand extends Command {

    public CheckDisguiseCommand() {
        super("checkdisguise");
        setAliases(Collections.singletonList("checkd"));
        setDescription("rdisguise.staff");
        setUsage(CC.translate("&e/" + getLabel() + " <player>"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0){
            commandSender.sendMessage(getUsage());
            return true;
        }
        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage(CC.translate("&c" + strings[0] + " isn't online."));
            return true;
        }
        Profile profile = Profile.get(player.getUniqueId());
        if (!profile.isDisguise()) {
            commandSender.sendMessage(CC.translate("&c" + player.getName() + " isn't disguise."));
            return true;
        }
        commandSender.sendMessage(CC.translate("&7&m-------------------------"));
        commandSender.sendMessage(CC.translate("&a" + profile.getPlayerDisguiseData().getCacheDisguiseData().getRealName()
                + " &f-> &a" + profile.getPlayerDisguiseData().getCacheDisguiseData().getDisguiseName()));
        commandSender.sendMessage(CC.translate("&7&m-------------------------"));
        return false;
    }
}
