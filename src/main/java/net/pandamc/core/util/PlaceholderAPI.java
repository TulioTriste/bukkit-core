package net.pandamc.core.util;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.pandamc.core.Core;
import net.pandamc.core.profile.Profile;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "bukkit-core";
    }

    @Override
    public @NotNull String getAuthor() {
        return Core.get().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return Core.get().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return null;

        Profile profile = Profile.get(player.getUniqueId());
        if (params.equalsIgnoreCase("tag_display")) {
            return profile.getTag() == null ? "" : CC.translate(profile.getTag().getDisplay());
        }
        else if (params.equalsIgnoreCase("tag_name")) {
            return profile.getTag() == null ? "" : profile.getTag().getName();
        }
        return null;
    }
}
