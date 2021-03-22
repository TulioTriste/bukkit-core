package net.pandamc.core.commands;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.TaskUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListCommand extends Command {

    private final Core plugin = Core.get();

    public ListCommand() {
        super("list");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        TaskUtil.runAsync(() -> {
            commandSender.sendMessage(CC.translate("&7" + plugin.getServer().getOnlinePlayers().size() +
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
            commandSender.sendMessage(message.substring(0, message.length() - 2));
        });
        return false;
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
