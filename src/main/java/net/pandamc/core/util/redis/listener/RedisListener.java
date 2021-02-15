package net.pandamc.core.util.redis.listener;

import com.google.gson.Gson;
import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

    private final Core plugin = Core.get();

    @Override
    public void onMessage(String channel, String message) {
        var redisMessage = new Gson().fromJson(message, RedisMessage.class);
        /*
        A switch is made to assign the action for each type of Payload, it can also be done by "if"
         */
        switch (redisMessage.getPayload()) {
            case STAFF_JOIN:
                var player = redisMessage.getParam("PLAYER");
                var prefixPlayer = redisMessage.getParam("PREFIX");
                var server = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bukkit.core.staff")) {
                        players.sendMessage(CC.translate("&9[Staff] &7(" + server + ") " + prefixPlayer + player + " &bhas joined"));
                    }
                });
                break;
            case STAFF_CHAT:
                var player1 = redisMessage.getParam("PLAYER");
                var prefixPlayer1 = redisMessage.getParam("PREFIX");
                var server1 = redisMessage.getParam("SERVER");
                var message1 = redisMessage.getParam("MESSAGE");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bukkit.core.staff")) {
                        players.sendMessage(CC.translate("&9[Staff] &7(" + server1 + ") " + prefixPlayer1 + player1+ "&7: &r" + message1));
                    }
                });
                break;
            case LOAD_SERVER:
                var server2 = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bungee.core.admin") || players.isOnline()) {
                        players.sendMessage(CC.translate("&9[" + server2 + "] &bhas been &aOpened&9!"));
                    }
                });
                break;
            case DISABLE_SERVER:
                var server3 = redisMessage.getParam("SERVER");
                Bukkit.getOnlinePlayers().forEach(players -> {
                    if (players.hasPermission("bungee.core.admin") || players.isOnline()) {
                        players.sendMessage(CC.translate("&9[" + server3 + "] &bhas been &cClosed&9!"));
                    }
                });
                break;
            case CLOSE_SERVER:
                var server4 = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server4)) {
                    Bukkit.shutdown();
                }
                break;
            case ENABLE_WHITELIST:
                var server5 = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server5)) Bukkit.setWhitelist(true);
                break;
            case DISABLE_WHITELIST:
                var server6 = redisMessage.getParam("SERVER");
                if (plugin.getServerName().equals(server6)) Bukkit.setWhitelist(false);
                break;
            default:
                plugin.getLogger().info("[Redis] The message was received, but there was no response");
                break;
        }
    }
}
