package net.pandamc.core.tags.commands;

import net.pandamc.core.util.CC;
import net.pandamc.core.util.command.BaseCommand;
import net.pandamc.core.util.command.Command;
import net.pandamc.core.util.command.CommandArgs;
import net.pandamc.core.util.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TagCommand extends BaseCommand {

    @Command(name = "tag")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String label = command.getLabel();
        String[] args = command.getArgs();

        openTagMenu(player, label);
    }

    public void openTagMenu(Player player, String label) {
        Inventory menu = Bukkit.createInventory(player, 45, CC.translate("&2Tag Menu"));

        menu.setItem(11, new ItemBuilder(Material.NAME_TAG).name("&fComunes").build());
        menu.setItem(13, new ItemBuilder(Material.NAME_TAG).name("&aPaises").build());
        menu.setItem(15, new ItemBuilder(Material.NAME_TAG).name("&6Premium").build());
        menu.setItem(29, new ItemBuilder(Material.NAME_TAG).name("&bHashTag").build());
        menu.setItem(31, new ItemBuilder(Material.REDSTONE).name("&cReset your " + "label").build());
        menu.setItem(33, new ItemBuilder(Material.NAME_TAG).name("&5Custom").build());

        player.openInventory(menu);
    }
}
