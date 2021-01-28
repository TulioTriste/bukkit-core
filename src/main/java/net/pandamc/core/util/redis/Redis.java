package net.pandamc.core.util.redis;

import lombok.Getter;
import net.pandamc.core.Core;
import net.pandamc.core.util.redis.listener.RedisListener;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Getter
public class Redis {

    JedisPool jedisPool;

    RedisListener redisListener;

    private final String ip = Core.get().getMainConfig().getString("REDIS.HOST");

    private final int port = Core.get().getMainConfig().getInteger("REDIS.PORT");

    private final String password = Core.get().getMainConfig().getString("REDIS.AUTHENTICATION.PASSWORD");

    private final boolean auth = Core.get().getMainConfig().getBoolean("REDIS.AUTHENTICATION.ENABLED");

    @Getter private boolean active = false;

    public void connect() {
        try {
            Core.get().getLogger().info("Connecting to redis");
            this.jedisPool = new JedisPool(ip, port);
            Jedis jedis = this.jedisPool.getResource();
            if (auth)
                if (password != null || !password.equals(Strings.EMPTY))
                    jedis.auth(this.password);
            this.redisListener = new RedisListener();
            (new Thread(() -> jedis.subscribe(this.redisListener, "bukkit-core"))).start();
            jedis.connect();
            active = true;
            Core.get().getLogger().info("Successfully redis connection.");
        } catch (Exception e) {
            Core.get().getLogger().info("Error in redis connection.");
            active = false;
        }
    }

    public void disconnect() {
        this.redisListener.unsubscribe();
        jedisPool.destroy();
    }

    public void write(String json){
        try (Jedis jedis = this.jedisPool.getResource()) {
            if (auth) {
                if (password != null || !password.equals(""))
                    jedis.auth(this.password);
            }
            jedis.publish("bukkit-core", json);
        }
    }
}
