package net.pandamc.core.tags.commands;

import net.pandamc.core.tags.menu.TagMenu;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class TagCommand extends BaseCommand {

    @Command(name = "tag", aliases = {"tags", "prefix", "prefixs"})
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        new TagMenu().openMenu(player);
    }
}
