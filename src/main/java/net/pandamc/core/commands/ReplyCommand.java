package net.pandamc.core.commands;

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

		Player player = (Player) commandSender;

		if (strings.length < 1) {
			player.sendMessage(CC.translate("&cUsage: /" + label + " <message>"));
			return true;
		}

		Player target = MessageCommand.getInstance().lastMessage.get(player);

		if (target == null) {
			player.sendMessage(CC.translate("&cNothing to reply."));
			return true;
		}

		StringBuilder message = new StringBuilder();

		for (int i = 0; i != strings.length; i++) {
			message.append(strings[i]).append(" ");
		}

		String playerName = Core.get().getRankManager().getRankPrefix(player) + player.getName();
		String targetName = Core.get().getRankManager().getRankPrefix(target) + target.getName();

		target.playSound(target.getLocation(), org.bukkit.Sound.ORB_PICKUP, 1F, 1F);

		player.sendMessage(CC.translate(ColorMessageButton.getColorMessage(player)
				+ "(To " + targetName + ColorMessageButton.getColorMessage(player) + ") " + message));
		target.sendMessage(CC.translate(ColorMessageButton.getColorMessage(target)
				+ "(From " + playerName + ColorMessageButton.getColorMessage(target) + ") " + message));

		MessageCommand.getInstance().lastMessage.put(player, target);
		MessageCommand.getInstance().lastMessage.put(target, player);
		return true;
	}
}
