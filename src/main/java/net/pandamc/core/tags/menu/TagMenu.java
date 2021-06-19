package net.pandamc.core.tags.menu;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import net.pandamc.core.profile.Profile;
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

public class TagMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "&2Tag Menu";
    }

    @Override
    public int getSize() {
        return 9*5;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        buttons.put(11, new TagsTypeButton(TagType.COMUNES));
        buttons.put(13, new TagsTypeButton(TagType.PAISES));
        buttons.put(15, new TagsTypeButton(TagType.PREMIUM));
        buttons.put(29, new TagsTypeButton(TagType.HASHTAG));
        buttons.put(33, new TagsTypeButton(TagType.CUSTOM));
        buttons.put(31, new ResetTagButton());
        return buttons;
    }

    @RequiredArgsConstructor
    private static class TagsTypeButton extends Button {

        private final TagType type;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.NAME_TAG)
                    .name("&f" + type.getName())
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
            new TagsTypeMenu(type).openMenu(player);
        }
    }

    private static class ResetTagButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            Profile profile = Profile.get(player.getUniqueId());
            return new ItemBuilder(Material.REDSTONE_BLOCK)
                    .name("&cReset your Tag")
                    .lore("&7Your Current Tag: " + (profile.getTag() != null ? profile.getTag().getDisplay() : "None"))
                    .build();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {
            Profile.get(player.getUniqueId()).setTag(null);
            player.sendMessage(CC.translate("&cTag resetado"));
        }
    }
}
