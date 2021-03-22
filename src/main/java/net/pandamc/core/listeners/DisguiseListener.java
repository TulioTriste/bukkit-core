package net.pandamc.core.listeners;

import net.pandamc.core.Core;
import net.pandamc.core.disguise.CacheDisguiseData;
import net.pandamc.core.disguise.DisguiseManager;
import net.pandamc.core.disguise.events.PlayerDisguiseEvent;
import net.pandamc.core.disguise.events.PlayerJoinDisguiseEvent;
import net.pandamc.core.disguise.events.PlayerUnDisguiseEvent;
import net.pandamc.core.disguise.skin.Skin;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:39
 */

public class DisguiseListener implements Listener {

    private final DisguiseManager disguiseManager = Core.get().getDisguiseManager();

    @EventHandler
    public void onDisguiseEvent(PlayerDisguiseEvent event) {
        if (event.isCancelled()) return;
        Profile profile = Profile.get(event.getPlayer().getUniqueId());
        Skin skin = disguiseManager.getRandomSkin();
        String name = disguiseManager.getRandomName();
        if (name == null) {
            profile.getPlayer().sendMessage(CC.translate("&cFailed to found a new disguise name."));
            return;
        }
        disguiseManager.handlerDisguise(profile, new CacheDisguiseData(profile.getUuid(), profile.getName(), name, Skin.DEFAULT_SKIN, skin));
    }

    @EventHandler
    public void onUnDisguise(PlayerUnDisguiseEvent event) {
        if (event.isCancelled()) return;
        disguiseManager.handlerUnDisguise(Profile.get(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onJoinDisguise(PlayerJoinDisguiseEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        CacheDisguiseData cacheDisguiseData = event.getPlayerDisguiseData().getCacheDisguiseData();
        if (!disguiseManager.getDisguiseAvailableNames().contains(cacheDisguiseData.getDisguiseName()) && !disguiseManager.getCacheDataNames().contains(cacheDisguiseData.getDisguiseName())) {
            player.sendMessage(CC.translate("&aTrying to get a new name, yours is already in use."));
            String name = disguiseManager.getRandomName();
            if (name == null) {
                player.sendMessage(CC.translate("&cFailed to found a new disguise name."));
                return;
            }
            cacheDisguiseData.setDisguiseName(name);
        }
        disguiseManager.getCacheDataNames().remove(cacheDisguiseData.getDisguiseName());
        disguiseManager.handlerDisguise(Profile.get(event.getPlayer().getUniqueId()), cacheDisguiseData, true);
    }

//    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
//    public void onChat(AsyncPlayerChatEvent event) {
//        Player player = event.getPlayer();
//        Profile data = PlayerData.getData(player.getUniqueId());
//        if (!data.isDisguise()) return;
//        String format = CC.translate(Lang.CHAT_FORMAT.toString())
//                .replace("<player>", data.getPlayerDisguiseData().getCacheDisguiseData().getDisguiseName());
//
//        event.setFormat(format.replace("<message>", event.getMessage()));
//    }
}
