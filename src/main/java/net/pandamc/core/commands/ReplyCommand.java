package net.pandamc.core.commands;

import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReplyCommand extends Command {
	
	public ReplyCommand() {
		super("reply");
		this.setAliases(Arrays.asList("r"));
	}

	@Override
	public boolean execute(CommandSender commandSender, String s, String[] strings) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(CC.translate("&cNo Console."));
			return true;
		}

		var player = (Player) commandSender;

		if (strings.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /" + s + " <message>"));
			return true;
		}

		var target = MessageCommand.getInstance().lastMessage.get(player);

		if (target == null) {
			player.sendMessage(CC.translate("&cNothing to reply."));
			return true;
		}

		var message = new StringBuilder();

		for (int i = 0; i != strings.length; i++) {
			message.append(strings[i]).append(" ");
		}

		var playerName = Core.get().getRankManager().getRankPrefix(player) + player.getName();
		var targetName = Core.get().getRankManager().getRankPrefix(target) + target.getName();

		target.playSound(target.getLocation(), org.bukkit.Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) &r" + message));
		target.sendMessage(CC.translate("&7(From " + playerName + "&7) &r" + message));

		MessageCommand.getInstance().lastMessage.put(player, target);
		MessageCommand.getInstance().lastMessage.put(target, player);
		return true;
	}
}
