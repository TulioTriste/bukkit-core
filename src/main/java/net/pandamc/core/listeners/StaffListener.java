package net.pandamc.core.listeners;

import net.pandamc.core.Core;
import net.pandamc.core.commands.StaffChatCommand;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StaffListener implements Listener {

    private final Core plugin = Core.get();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("bukkit.core.staff")) {
            String json = new RedisMessage(Payload.STAFF_JOIN)
                    .setParam("PLAYER", player.getName())
                    .setParam("PREFIX", plugin.getRankManager().getRankPrefix(player))
                    .setParam("SERVER", plugin.getServerName())
                    .toJSON();

            plugin.getRedisManager().write(json);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (StaffChatCommand.INSTANCE.contains(player.getUniqueId())) {
            event.setCancelled(true);
            String json = new RedisMessage(Payload.STAFF_CHAT)
                    .setParam("PLAYER", player.getName())
                    .setParam("PREFIX", plugin.getRankManager().getRankPrefix(player))
                    .setParam("SERVER", plugin.getServerName())
                    .setParam("MESSAGE", event.getMessage())
                    .toJSON();
            Core.get().getRedisManager().write(json);
        }
    }
}
