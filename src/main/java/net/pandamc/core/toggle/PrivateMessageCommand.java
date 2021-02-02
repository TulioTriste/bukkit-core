package net.pandamc.core.toggle;

import com.google.common.collect.Sets;
import lombok.Getter;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Getter
public class PrivateMessageCommand extends Command {

	@Getter
	private static PrivateMessageCommand instance;
	private final Set<UUID> privateMessage = Sets.newHashSet();

	public PrivateMessageCommand() {
		super("toggleprivatemessage");
		this.setAliases(Arrays.asList("togglepm", "togglemsg"));
		this.setPermission("bukkit.core.toggleprivatemessage");
		instance = this;
	}

	@Override
	public boolean execute(CommandSender commandSender, String s, String[] strings) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(CC.translate("&cNo Console."));
			return true;
		}

		Player player = (Player) commandSender;

		if (!player.hasPermission(getPermission())) {
			player.sendMessage(CC.translate("&cNo Permissions."));
			return true;
		}

		if (this.privateMessage.contains(player.getUniqueId())) {
			this.privateMessage.remove(player.getUniqueId());
//			player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.ENABLE")));
		}
		else {
			this.privateMessage.add(player.getUniqueId());
//			player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.DISABLE")));
		}
		return true;
	}
}
