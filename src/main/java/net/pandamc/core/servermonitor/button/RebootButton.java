package net.pandamc.core.servermonitor.button;

import lombok.AllArgsConstructor;
import net.pandamc.core.Core;
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
public class RebootButton extends Button {

    public String server;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.REDSTONE)
                .name("&bReboot Server")
                .lore("",
                        " &7\u27A8 &cClick to Reboot " + server)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
        String json = new RedisMessage(Payload.CLOSE_SERVER)
                .setParam("SERVER", server)
                .toJSON();
        Core.get().getRedisManager().write(json);
        player.sendMessage(CC.translate("&cServer Closed Correctly!"));
    }
}
