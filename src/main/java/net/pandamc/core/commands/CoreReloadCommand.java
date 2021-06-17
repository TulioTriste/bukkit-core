package net.pandamc.core.commands;

import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.command.CommandSender;

public class CoreReloadCommand extends BaseCommand {

    @Command(name = "corereload", permission = "bukkit.core.admin", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs commandArgs) {
        CommandSender sender = commandArgs.getSender();
        if (!sender.hasPermission("bukkit.core.admin")) {
            sender.sendMessage(CC.translate("&cNo Permissions."));
        }
        Core.get().getMainConfig().reload();
    }
}
