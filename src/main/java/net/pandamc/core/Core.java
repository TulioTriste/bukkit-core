package net.pandamc.core;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.pandamc.core.commands.*;
import net.pandamc.core.listeners.PlayerListener;
import net.pandamc.core.listeners.ServerListener;
import net.pandamc.core.listeners.StaffListener;
import net.pandamc.core.rank.Rank;
import net.pandamc.core.rank.RankManager;
import net.pandamc.core.tags.commands.TagCommand;
import net.pandamc.core.task.VipsOnlineTask;
import net.pandamc.core.toggle.PrivateMessageCommand;
import net.pandamc.core.util.TaskUtil;
import net.pandamc.core.util.command.CommandManager;
import net.pandamc.core.util.file.type.BasicConfigurationFile;
import net.pandamc.core.util.menu.MenuListener;
import net.pandamc.core.util.redis.Redis;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter @Setter
public class Core extends JavaPlugin {

    private BasicConfigurationFile mainConfig, coloredRanksConfig, tagsConfig;
    private Redis redisManager;
    private String serverName, rankSystem;
    private Chat chat;
    private RankManager rankManager;
    private MongoDatabase mongoDatabase;

    public static Core get() {
        return getPlugin(Core.class);
    }

    @Override
    public void onEnable() {
        registerConfigs();
        registerMongoDB();
        registerManagers();
        registerListeners();
        registerCommands();
        loadTasks();
        sendInfoLoad();
    }

    @Override
    public void onDisable() {
        sendInfoClosed();
        this.redisManager.disconnect();
    }

    private void registerConfigs() {
        this.mainConfig = new BasicConfigurationFile(this, "config");
        this.coloredRanksConfig = new BasicConfigurationFile(this, "colored-ranks");
        this.tagsConfig = new BasicConfigurationFile(this, "tags");
    }

    private void registerManagers() {
        this.redisManager = new Redis();
        Rank.init();
        this.redisManager.connect();
        this.serverName = getMainConfig().getString("SERVER");
    }

    private void registerCommands() {
        new CommandManager(this);
        if (mainConfig.getBoolean("MESSAGE_REPLY_BOOLEAN")) {
            new MessageCommand();
            new ReplyCommand();
            new PrivateMessageCommand();
        }
        new StaffChatCommand();
        new AdminChatCommand();
        new CoreReloadCommand();
        new ServerMonitorCommand();
        new ListCommand();
        new TagCommand();
    }

    private void registerListeners() {
        Arrays.asList(new StaffListener(),
                new PlayerListener(),
                new MenuListener(),
                new ServerListener())
                .forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void loadTasks() {
        if (mainConfig.getBoolean("ONLINE_DONATORS")) TaskUtil.runTimer(new VipsOnlineTask(), 2400L, 2400L);
    }

    public void loadVault() {
        RegisteredServiceProvider<Chat> provider = getServer().getServicesManager().getRegistration(Chat.class);
        if (provider != null) chat = provider.getProvider();
    }

    private void sendInfoLoad() {
        String json = new RedisMessage(Payload.LOAD_SERVER)
                .setParam("SERVER", getServerName())
                .toJSON();
        redisManager.write(json);
    }

    private void sendInfoClosed() {
        String json = new RedisMessage(Payload.DISABLE_SERVER)
                .setParam("SERVER", getServerName())
                .toJSON();
        redisManager.write(json);
    }

    private void registerMongoDB() {
        if (mainConfig.getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
            mongoDatabase = new MongoClient(
                    new ServerAddress(
                            mainConfig.getString("MONGO.HOST"),
                            mainConfig.getInteger("MONGO.PORT")
                    ),
                    MongoCredential.createCredential(
                            mainConfig.getString("MONGO.AUTHENTICATION.USERNAME"),
                            mainConfig.getString("MONGO.AUTHENTICATION.DATABASE"),
                            mainConfig.getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            ).getDatabase(mainConfig.getString("MONGO.DATABASE"));
        } else {
            mongoDatabase = new MongoClient(mainConfig.getString("MONGO.HOST"), mainConfig.getInteger("MONGO.PORT"))
                    .getDatabase(mainConfig.getString("MONGO.DATABASE"));
        }
    }
}
