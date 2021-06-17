package net.pandamc.core.servermonitor.button;

import net.pandamc.core.util.item.ItemBuilder;
import lombok.AllArgsConstructor;
import net.pandamc.core.servermonitor.menu.ServerConfigurationMenu;
import net.pandamc.core.util.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ServerMonitorButton extends Button {

    String server;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.BEACON)
                .name("&b" + server)
                .lore("",
                        "&cClick to Open Configuration Options!")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        new ServerConfigurationMenu(server).openMenu(player);
    }
}