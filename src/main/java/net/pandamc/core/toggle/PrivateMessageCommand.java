package net.pandamc.core.toggle;

import com.google.common.collect.Sets;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import lombok.Getter;
import net.pandamc.core.util.CC;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

@Getter
public class PrivateMessageCommand extends BaseCommand {

	@Getter public static final Set<UUID> privateMessage = Sets.newHashSet();

	@Command(name = "toggleprivatemessage", aliases = {"togglepm", "togglemsg"}, permission = "bukkit.core.toggleprivatemessage")
	@Override
	public void onCommand(CommandArgs commandArgs) {
		Player player = commandArgs.getPlayer();

		if (!player.hasPermission("bukkit.core.toggleprivatemessage")) {
			player.sendMessage(CC.translate("&cNo Permissions."));
			return;
		}

		if (getPrivateMessage().contains(player.getUniqueId())) {
			getPrivateMessage().remove(player.getUniqueId());
//			player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.ENABLE")));
		}
		else {
			getPrivateMessage().add(player.getUniqueId());
//			player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.DISABLE")));
		}
	}
}
