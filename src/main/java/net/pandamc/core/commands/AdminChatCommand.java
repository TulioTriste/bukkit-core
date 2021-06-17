package net.pandamc.core.commands;

import com.google.common.collect.Sets;
import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class AdminChatCommand extends BaseCommand {

    public static Set<UUID> INSTANCE = Sets.newHashSet();

    @Command(name = "adminchat", aliases = {"ac", "achat", "adminc"}, permission = "bukkit.core.admin")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String label = commandArgs.getLabel();
        String[] args = commandArgs.getArgs();

        if (!player.hasPermission("bukkit.core.admin")) {
            player.sendMessage(CC.translate("&cNo Permissions."));
            return;
        }
        if (args.length == 0) {
            if (INSTANCE.contains(player.getUniqueId())) {
                INSTANCE.remove(player.getUniqueId());
                player.sendMessage(CC.translate("&4[Admin] &cAdminChat &cDisabled"));
            } else {
                INSTANCE.add(player.getUniqueId());
                player.sendMessage(CC.translate("&4[Staff] &cAdminChat &aEnabled"));
            }
            return;
        }
        var message = new StringBuilder();
        for (var string : args) {
            message.append(string).append(" ");
        }
        var json = new RedisMessage(Payload.ADMIN_CHAT)
                .setParam("PLAYER", player.getName())
                .setParam("PREFIX", Core.get().getRankManager().getRankPrefix(player))
                .setParam("SERVER", Core.get().getServerName())
                .setParam("MESSAGE", message.toString())
                .toJSON();
        Core.get().getRedisManager().write(json);
    }
}
