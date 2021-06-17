package net.pandamc.core.commands;

import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReplyCommand extends BaseCommand {

	@Command(name = "reply", aliases = "r")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();
		String label = commandArgs.getLabel();
		String[] args = commandArgs.getArgs();

		if (args.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <message>"));
			return;
		}

		Player target = MessageCommand.getInstance().lastMessage.get(player);

		if (target == null) {
			player.sendMessage(CC.translate("&cNothing to reply."));
			return;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 0; i != args.length; i++) {
			message.append(args[i]).append(" ");
		}

		var playerName = Core.get().getRankManager().getRankPrefix(player) + player.getName();
		var targetName = Core.get().getRankManager().getRankPrefix(target) + target.getName();

		target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate("&7(To " + targetName + "&7) &r" + message));
		target.sendMessage(CC.translate("&7(From " + playerName + "&7) &r" + message));

		MessageCommand.getInstance().lastMessage.put(player, target);
		MessageCommand.getInstance().lastMessage.put(target, player);
	}
}
