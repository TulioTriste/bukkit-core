package net.pandamc.core.commands;

import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffChatCommand extends Command {

    public static Set<UUID> INSTANCE;

    static {
        INSTANCE = new HashSet<>();
    }

    public StaffChatCommand() {
        super("staffchat", "StaffChat command", null, Arrays.asList("sc", "schat", "staffc"));
        this.setPermission("bukkit.core.staff");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(CC.translate("&cNo Console."));
            return true;
        }
        else if (!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(CC.translate("&cNo Permissions."));
            return true;
        }
        Player player = (Player) commandSender;
        if (strings.length == 0) {
            if (INSTANCE.contains(player.getUniqueId())) {
                INSTANCE.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&9[Staff] &bStaffChat &cDisabled"));
            } else {
                INSTANCE.add(player.getUniqueId());
                player.sendMessage(CC.translate("&9[Staff] &bStaffChat &aEnabled"));
            }
            return true;
        }
        StringBuilder message = new StringBuilder();
        for (String string : strings) {
            message.append(string);
        }
        String json = new RedisMessage(Payload.STAFF_CHAT)
                .setParam("PLAYER", player.getName())
                .setParam("PREFIX", Core.get().getChat().getPlayerPrefix(player))
                .setParam("SERVER", Core.get().getServerName())
                .setParam("MESSAGE", message.toString())
                .toJSON();
        Core.get().getRedisManager().write(json);
        return false;
    }
}
