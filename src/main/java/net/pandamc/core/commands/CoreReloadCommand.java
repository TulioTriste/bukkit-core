package net.pandamc.core.commands;

import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CoreReloadCommand extends Command {

    public CoreReloadCommand() {
        super("corereload");
        this.setPermission("bukkit.core.admin");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.hasPermission(getPermission())) {
            commandSender.sendMessage(CC.translate("&cNo Permissions."));
            return true;
        }
        Core.get().getMainConfig().reload();
        return false;
    }
}
