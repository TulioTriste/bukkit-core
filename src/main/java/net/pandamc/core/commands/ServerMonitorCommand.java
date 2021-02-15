package net.pandamc.core.commands;

import lombok.var;
import net.pandamc.core.servermonitor.menu.ServerMonitorMenu;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ServerMonitorCommand extends Command {

    public ServerMonitorCommand() {
        super("servermonitor");
        this.setPermission("bukkit.core.servermonitor");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(CC.translate("&cNo Console."));
            return true;
        }
        if (!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(CC.translate("&cNo Permissions."));
            return true;
        }
        var player = (Player) commandSender;
        new ServerMonitorMenu().openMenu(player);
        return false;
    }
}
