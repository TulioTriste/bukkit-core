package net.pandamc.core.servermonitor.button;

import net.pandamc.core.util.item.ItemBuilder;
import lombok.AllArgsConstructor;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class WhitelistButton extends Button {

    public String server;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.PAPER)
                .name("&bWhitelist")
                .lore("",
                        " &7\u27A8 &aRight Click to Enable Whitelist!",
                        "",
                        " &7\u27A8 &cLeft Click to Disable Whitelist!")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        if (clickType.isLeftClick()) {
            String json = new RedisMessage(Payload.DISABLE_WHITELIST)
                    .setParam("SERVER", server)
                    .toJSON();
            Core.get().getRedisManager().write(json);
            player.sendMessage(CC.translate("&c" + server + " Whitelist Disabled!"));
        }
        else if (clickType.isRightClick()) {
            String json = new RedisMessage(Payload.ENABLE_WHITELIST)
                    .setParam("SERVER", server)
                    .toJSON();
            Core.get().getRedisManager().write(json);
            player.sendMessage(CC.translate("&a" + server + " Whitelist Enabled"));
        }
    }
}
