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
 * Date: 14/02/2021 @ 04:15
 */

public class ForceUnDisguiseCommand extends Command {

    public ForceUnDisguiseCommand() {
        super("forceundisguise");
        setAliases(Collections.singletonList("forceud"));
        setDescription("rdisguise.admin");
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
            commandSender.sendMessage(CC.translate("&a" + player.getName() + " &cis already undisguise."));
            return true;
        }
        player.sendMessage(CC.translate("&a" + commandSender.getName() + "'s forced your to use un-disguise"));
        commandSender.sendMessage(CC.translate("&aYou forced " + player.getName() + " to use un-disguise"));
        player.performCommand("ud");
        return false;
    }
}