package net.pandamc.core.commands;

import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import net.pandamc.core.servermonitor.menu.ServerMonitorMenu;
import net.pandamc.core.util.CC;
import org.bukkit.entity.Player;

public class ServerMonitorCommand extends BaseCommand {

    @Command(name = "servermonitor", permission = "bukkit.core.servermonitor")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        if (!player.hasPermission("bukkit.core.servermonitor")) {
            player.sendMessage(CC.translate("&cNo Permissions."));
            return;
        }
        new ServerMonitorMenu().openMenu(player);
    }
}
