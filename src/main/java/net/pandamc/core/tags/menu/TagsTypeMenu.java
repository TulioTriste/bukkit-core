package net.pandamc.core.tags.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.tags.Tag;
import net.pandamc.core.tags.TagType;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.item.ItemBuilder;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@RequiredArgsConstructor
public class TagsTypeMenu extends Menu {

    private final TagType type;

    @Override
    public String getTitle(Player player) {
        return "&fTag " + type.getName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        Tag.getTagStorage().stream().filter(tag -> tag.getType() == type).forEach(tag -> {
            buttons.put(buttons.size(), new TagButton(tag));
        });
        return buttons;
    }

    @RequiredArgsConstructor
    private static class TagButton extends Button {

        private final Tag tag;

        @Override
        public ItemStack getButtonItem(Player player) {
            if (player.hasPermission("gtag." + tag.getName()) || player.hasPermission("deluxetags.tag." + tag.getName())) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .data(5)
                        .name("&f" + tag.getName())
                        .lore("",
                                " &8» &2&lGalanthus&a&lMC &8I &2Prefixes",
                                " &8» &7Click izquierdo para equipar",
                                "&7Prefix: " + tag.getDisplay(),
                                "")
                        .build();
            } else {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .data(14)
                        .name("&f" + tag.getName())
                        .lore("",
                                " &8» &2&lGalanthus&a&lMC &8I &2Prefixes",
                                " &8» &7Compralo por tienda.galanthusmc.net",
                                "&7Prefix: " + tag.getDisplay(),
                                "")
                        .build();
            }
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
            if (player.hasPermission("gtag." + tag.getName()) || player.hasPermission("deluxetags.tag." + tag.getName())) {
                Profile.get(player.getUniqueId()).setTag(tag);
                player.sendMessage(CC.translate("&aTu tag se ha actualizado a " + tag.getDisplay()));
            } else {
                player.sendMessage(CC.translate("&cNo tienes permisos para usar este Tag"));
            }
        }
    }
}
