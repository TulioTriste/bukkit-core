package net.pandamc.core.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.toggle.PrivateMessageCommand;
import net.pandamc.core.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.Getter;

public class MessageCommand extends Command {

	@Getter
	private static MessageCommand instance;
	public Map<Player, Player> lastMessage;

	public MessageCommand() {
		super("message");
		this.setAliases(Arrays.asList("msg"));
		instance = this;
		lastMessage = new HashMap<>();
	}

	@Override
	public boolean execute(CommandSender commandSender, String s, String[] strings) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(CC.translate("&cNo Console."));
			return true;
		}

		var player = (Player) commandSender;

		if (!player.hasPermission("pandahub.message")) {
			player.sendMessage(CC.translate("&cNo Permissions."));
			return true;
		}

		if (strings.length < 2) {
			player.sendMessage(CC.translate("&cUsage: /" + s + " <player> <message>"));
			return true;
		}

		var target = Bukkit.getPlayer(strings[0]);

		if (target == null) {
			player.sendMessage(CC.translate("&cPlayer not found."));
			return true;
		}

		if (PrivateMessageCommand.getInstance().getPrivateMessage().contains(target.getUniqueId())) {
			player.sendMessage(CC.translate("&c" + target.getName() + " has private messages disabled."));
			return true;
		}

		var message = new StringBuilder();

		for (var i = 1; i != strings.length; i++) {
			message.append(strings[i]).append(" ");
		}

		var playerName = Core.get().getRankManager().getRankPrefix(player) + player.getName();
		var targetName = Core.get().getRankManager().getRankPrefix(target) + target.getName();

		target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) &r" + message));
		target.sendMessage(CC.translate( "&7(From " + playerName + "&7) &r" + message));

		lastMessage.put(player, target);
		lastMessage.put(target, player);
		return true;
	}
}
