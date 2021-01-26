package net.pandamc.core.commands;

import lombok.AllArgsConstructor;
import net.pandamc.core.Core;
import net.pandamc.core.servermonitor.menu.ServerMonitorMenu;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.item.ItemBuilder;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.menu.Menu;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Player player = (Player) commandSender;
        new ServerMonitorMenu().openMenu(player);
        return false;
    }
}
