package net.pandamc.core.listeners;

import com.lunarclient.bukkitapi.LunarClientAPI;
import com.lunarclient.bukkitapi.object.LCNotification;
import lombok.var;
import net.pandamc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {

    private final Core plugin = Core.get();
    private final List<String> blacklist = Arrays.asList("galanthus", "galanthusmc", "ghostly", "ghostlymc", "ghostlylive",
            "skillwars", "skillwarsmc", "skillwarsnet", "holy", "holymc", "holynet", "koru", "korurip", "korumc", "hazel",
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
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        var message = event.getMessage();

        if (!event.getPlayer().hasPermission("bukkit.core.staff") || !event.getPlayer().isOp()) {
            blacklist.forEach(words -> {
                for (var s : message.split(" ")) {
                    if (s.toLowerCase().equals(words) || s.toLowerCase().startsWith(words) || s.toLowerCase().endsWith(words)) {
                        event.setMessage(replacer.get(ThreadLocalRandom.current().nextInt(replacer.size())));
                    }
                }
            });
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (event.getMessage().contains(player.getName()) && player.hasPermission("bukkit.core.staff")) {
                LunarClientAPI.getInstance().sendNotification(player, new LCNotification(ChatColor.GREEN + "They mentioned you", Duration.ofSeconds(2)));
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
            }
        });
    }
}
