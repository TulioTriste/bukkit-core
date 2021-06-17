package net.pandamc.core.util.redis.listener;

import com.google.gson.Gson;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.Bukkit;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

    private final Core plugin = Core.get();

    @Override
    public void onMessage(String channel, String message) {
        RedisMessage redisMessage = new Gson().fromJson(message, RedisMessage.class);
        /*
        A switch is made to assign the action for each type of Payload, it can also be done by "if"
         */
        switch (redisMessage.getPayload()) {
            case STAFF_JOIN: {
                String player = redisMessage.getParam("PLAYER");
                String prefix = redisMessage.getParam("PREFIX");
                String server = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bukkit.core.staff")) {
                        players.sendMessage(CC.translate("&9[Staff] &7(" + server + ") " + prefix + player + " &bhas joined"));
                    }
                });
            }
            break;
            case STAFF_CHAT: {
                String player = redisMessage.getParam("PLAYER");
                String prefix = redisMessage.getParam("PREFIX");
                String server = redisMessage.getParam("SERVER");
                String message1 = redisMessage.getParam("MESSAGE");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bukkit.core.staff")) {
                        players.sendMessage(CC.translate("&9[Staff] &7(" + server + ") " + prefix + player + "&7: &r" + message1));
                    }
                });
            }
            break;
            case ADMIN_CHAT: {
                String player = redisMessage.getParam("PLAYER");
                String prefix = redisMessage.getParam("PREFIX");
                String server = redisMessage.getParam("SERVER");
                String message1 = redisMessage.getParam("MESSAGE");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bukkit.core.admin")) {
                        players.sendMessage(CC.translate("&c[Admin] &7(" + server + ") " + prefix + player + "&7: &r" + message1));
                    }
                });
            }
            break;
            case LOAD_SERVER: {
                String server = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bungee.core.admin") || players.isOnline()) {
                        players.sendMessage(CC.translate("&9[" + server + "] &bhas been &aOpened&9!"));
                    }
                });
            }
            break;
            case DISABLE_SERVER: {
                String server = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bungee.core.admin") || players.isOnline()) {
                        players.sendMessage(CC.translate("&9[" + server + "] &bhas been &cClosed&9!"));
                    }
                });
            }
            break;
            case CLOSE_SERVER: {
                String server = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server)) {
                    Bukkit.shutdown();
                }
            }
            break;
            case ENABLE_WHITELIST: {
                String server = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server)) Bukkit.setWhitelist(true);
            }
            break;
            case DISABLE_WHITELIST: {
                String server = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server)) Bukkit.setWhitelist(false);
            }
            break;
            default: {
                plugin.getLogger().info("[Redis] The message was received, but there was no response");
            }
            break;
        }
    }
}
