package net.pandamc.core.listeners;

import net.pandamc.core.Core;
import net.pandamc.core.nametags.GxNameTag;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.TaskUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {

    private final Core plugin = Core.get();
    private final List<String> blacklist = Arrays.asList("ghostly", "ghostlymc", "ghostlylive", "skillwars", "skillwarsmc",
            "skillwarsnet", "holy", "holymc", "holynet", "koru", "korurip", "korumc", "hazel",
            "hazelmc", "safe", "safemc", "safeclub", "sunpvp", "astral", "astralmc", "silex", "silexpvp", "silexmc",
            "kora", "korapvp", "koramc", "sololegends", "dynamicmc", "dynamicpvp", "water", "watermc", "waterpvp",
            "veax", "veaxmc", "veaxpvp", "veaxus", "veaxrip",
            "puto", "puta", "putita", "ez", "fuck", "nigga", "pussy", "zorra");
    private final List<String> replacer = Arrays.asList("Omg this server is the best I have seen in my life, and its kb I love",
            "I will ask my mother for the money to buy vip here!",
            "TulioTriste, the best programmer I've ever seen in my life",
            "I love this KnockBack created by ImMath",
            "I love PandaMC and the Panda Community plugins are the best!");

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {
        Profile profile = new Profile(event.getUniqueId());
        try {
            TaskUtil.runLaterAsync(profile::load, 20L);
            Profile.getProfiles().put(event.getUniqueId(), profile);
        } catch (Exception e) {
            e.printStackTrace();
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(CC.RED + "Failed to load your profile. Try again later.");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        Profile.get(player.getUniqueId()).setName(player.getName());

        if (Core.get().getMainConfig().getBoolean("NAMETAGS")) {
            TaskUtil.runAsync(() -> {
                if (GxNameTag.isInitiated()) {
                    player.setMetadata("sl-LoggedIn", new FixedMetadataValue(Core.get(), true));
                    GxNameTag.initiatePlayer(player);
                    GxNameTag.reloadPlayer(player);
                    GxNameTag.reloadOthersFor(player);
                }
            });
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        Profile.get(event.getPlayer().getUniqueId()).save();
        Profile.getProfiles().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        if (!event.getPlayer().hasPermission("bukkit.core.staff") || !event.getPlayer().isOp()) {
            blacklist.forEach(words -> {
                for (String s : message.split(" ")) {
                    if (s.toLowerCase().equals(words) || s.toLowerCase().startsWith(words) || s.toLowerCase().endsWith(words)) {
                        event.setMessage(replacer.get(ThreadLocalRandom.current().nextInt(replacer.size())));
                    }
                }
            });
        }

//        Bukkit.getOnlinePlayers().forEach(player -> {
//            if (event.getMessage().contains(player.getName()) && player.hasPermission("bukkit.core.staff")) {
//                LunarClientAPI.sendPacket(player, new LCNotification(ChatColor.GREEN + "They mentioned you", Duration.ofSeconds(2)));
//                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
//            }
//        });
    }
}
