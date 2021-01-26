package net.pandamc.core.servermonitor.button;

import lombok.AllArgsConstructor;
import net.pandamc.core.Core;
import net.pandamc.core.servermonitor.menu.ServerConfigurationMenu;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.item.ItemBuilder;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
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