package net.pandamc.core.commands;

import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.TaskUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListCommand extends BaseCommand {

    private final Core plugin = Core.get();

    @Command(name = "list", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs commandArgs) {
        CommandSender sender = commandArgs.getSender();
        TaskUtil.runAsync(() -> {
            sender.sendMessage(CC.translate("&7" + plugin.getServer().getOnlinePlayers().size() +
                    " Players out of " + plugin.getServer().getMaxPlayers() + " Connected"));
            List<Player> playerList = plugin.getServer().getOnlinePlayers().stream()
                    .sorted(new ComparatorPlayersWeight().reversed())
                    .collect(Collectors.toList());
            StringBuilder message = new StringBuilder();
            playerList.forEach(players -> {
                message.append(Core.get().getColoredRanksConfig().get("groups." + Core.get().getRankManager().getRankName(players)) != null ?
                        CC.translate(Core.get().getColoredRanksConfig().getString("groups." + Core.get().getRankManager().getRankName(players)))
                        : CC.translate("&r"))
                        .append(players.getName())
                        .append(CC.translate("&7, "));
            });
            sender.sendMessage(message.substring(0, message.length() - 2));
        });
    }

    private static class ComparatorPlayersWeight implements Comparator<Player> {

        @Override
        public int compare(Player o1, Player o2) {
            User user1 = LuckPermsProvider.get().getUserManager().getUser(o1.getUniqueId());
            User user2 = LuckPermsProvider.get().getUserManager().getUser(o2.getUniqueId());
            return Integer.compare(LuckPermsProvider.get().getGroupManager().getGroup(user1.getPrimaryGroup()).getWeight().orElse(0),
                    LuckPermsProvider.get().getGroupManager().getGroup(user2.getPrimaryGroup()).getWeight().orElse(0));
        }
    }
}
