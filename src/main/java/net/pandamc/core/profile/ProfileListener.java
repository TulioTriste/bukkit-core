package net.pandamc.core.profile;

import net.pandamc.core.Core;
import net.pandamc.core.disguise.events.PlayerJoinDisguiseEvent;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListener implements Listener {

    private final Core plugin = Core.get();

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        if (!event.getLoginResult().equals(AsyncPlayerPreLoginEvent.Result.ALLOWED)) return;

        if (event.getUniqueId() == null || event.getName() == null) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(CC.translate("&cFailed to load your profile."));
        }

        TaskUtil.runAsync(() -> {
            Profile profile = new Profile(event.getUniqueId());
            if (plugin.getMongoManager().hasData(event.getUniqueId())) {
                profile.loadData(plugin.getMongoManager().findData(event.getUniqueId()), true);
            }
            if (profile.isDisguise()) {
                System.out.println("ESTA KON /D");
                if (profile.getPlayerDisguiseData().canReDisguise()) {
                    System.out.println("PUEDE RELOADER");
                    System.out.println("JOIN /D EVENT");
                    new PlayerJoinDisguiseEvent(profile.getPlayer(), profile.getPlayerDisguiseData()).call();
                } else {
                    System.out.println("NULA");
                    profile.setPlayerDisguiseData(null);
                }
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Profile.getProfiles().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Profile.getProfiles().remove(event.getPlayer().getUniqueId());
    }
}
