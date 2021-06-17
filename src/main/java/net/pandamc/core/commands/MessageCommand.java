package net.pandamc.core.commands;

import java.util.Map;

import com.google.common.collect.Maps;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.toggle.PrivateMessageCommand;
import net.pandamc.core.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;

public class MessageCommand extends BaseCommand {

	@Getter private static MessageCommand instance;
	public Map<Player, Player> lastMessage = Maps.newHashMap();

	public MessageCommand() {
		instance = this;
	}

	@Command(name = "message", aliases = "msg")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String label = commandArgs.getLabel();
		String[] args = commandArgs.getArgs();

		if (!player.hasPermission("bukkit.core.message")) {
			player.sendMessage(CC.translate("&cNo Permissions."));
			return;
		}

		if (args.length < 2) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <player> <message>"));
			return;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			player.sendMessage(CC.translate("&cPlayer not found."));
			return;
		}

		if (PrivateMessageCommand.getPrivateMessage().contains(target.getUniqueId())) {
			player.sendMessage(CC.translate("&c" + target.getName() + " has private messages disabled."));
			return;
		}

		StringBuilder message = new StringBuilder();

		for (var i = 1; i != args.length; i++) {
			message.append(args[i]).append(" ");
		}

		String playerName = Core.get().getRankManager().getRankPrefix(player) + player.getName();
		String targetName = Core.get().getRankManager().getRankPrefix(target) + target.getName();

		target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) &r" + message));
		target.sendMessage(CC.translate( "&7(From " + playerName + "&7) &r" + message));

		lastMessage.put(player, target);
		lastMessage.put(target, player);
	}
}
