package net.pandamc.core.commands;

import com.google.common.collect.Sets;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StaffChatCommand extends BaseCommand {

    public static Set<UUID> INSTANCE = Sets.newHashSet();

    @Command(name = "staffchat", aliases = {"sc", "schat", "staffc"}, permission = "bukkit.core.staff")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String label = commandArgs.getLabel();
        String[] args = commandArgs.getArgs();

        if (!player.hasPermission("bukkit.core.staff")) {
            player.sendMessage(CC.translate("&cNo Permissions."));
            return;
        }
        if (args.length == 0) {
            if (INSTANCE.contains(player.getUniqueId())) {
                INSTANCE.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&9[Staff] &bStaffChat &cDisabled"));
            } else {
                INSTANCE.add(player.getUniqueId());
                player.sendMessage(CC.translate("&9[Staff] &bStaffChat &aEnabled"));
            }
            return;
        }
        var message = new StringBuilder();
        for (var string : args) {
            message.append(string).append(" ");
        }
        var json = new RedisMessage(Payload.STAFF_CHAT)
                .setParam("PLAYER", player.getName())
                .setParam("PREFIX", Core.get().getRankManager().getRankPrefix(player))
                .setParam("SERVER", Core.get().getServerName())
                .setParam("MESSAGE", message.toString())
                .toJSON();
        Core.get().getRedisManager().write(json);
    }
}
