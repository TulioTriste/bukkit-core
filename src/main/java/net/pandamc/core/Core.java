package net.pandamc.core;

import com.lunarclient.bukkitapi.LunarClientAPI;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.pandamc.core.commands.*;
import net.pandamc.core.database.impl.MongoHandler;
import net.pandamc.core.disguise.DisguiseAction;
import net.pandamc.core.disguise.DisguiseManager;
import net.pandamc.core.disguise.nms.DisguiseImplementation;
import net.pandamc.core.listeners.DisguiseListener;
import net.pandamc.core.listeners.PlayerListener;
import net.pandamc.core.listeners.ServerListener;
import net.pandamc.core.listeners.StaffListener;
import net.pandamc.core.profile.ProfileListener;
import net.pandamc.core.rank.Rank;
import net.pandamc.core.rank.RankManager;
import net.pandamc.core.task.VipsOnlineTask;
import net.pandamc.core.toggle.PrivateMessageCommand;
import net.pandamc.core.util.TaskUtil;
import net.pandamc.core.util.file.type.BasicConfigurationFile;
import net.pandamc.core.util.menu.MenuListener;
import net.pandamc.core.util.redis.Redis;
import net.pandamc.core.util.redis.impl.Payload;
import net.pandamc.core.util.redis.util.RedisMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class Core extends JavaPlugin {

    private BasicConfigurationFile mainConfig, coloredRanksConfig, disguiseConfig;
    private Redis redisManager;
    private String serverName, rankSystem;
    private Chat chat;
    private RankManager rankManager;
    private DisguiseImplementation disguiseImplementation;
    private DisguiseManager disguiseManager;
    private MongoHandler mongoManager;
    @Setter private List<DisguiseAction> disguiseActions = new ArrayList<>();

    public static Core get() {
        return getPlugin(Core.class);
    }

    @Override
    public void onEnable() {
        registerConfigs();
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
//        this.mongoManager.getMongoClient().close();
    }

    private void registerConfigs() {
        this.mainConfig = new BasicConfigurationFile(this, "config");
        this.coloredRanksConfig = new BasicConfigurationFile(this, "colored-ranks");
        this.disguiseConfig = new BasicConfigurationFile(this, "disguise");
    }

    private void registerManagers() {
        new LunarClientAPI(this);
        this.redisManager = new Redis();
        Rank.init();
        this.redisManager.connect();
        this.serverName = getMainConfig().getString("SERVER");
//        this.handlerNMS();
//        this.mongoManager = new MongoHandler();
//        this.mongoManager.connect();
//
//        this.disguiseManager = new DisguiseManager();
//        TaskUtil.runLaterAsync(disguiseManager::load, 20);
    }

    private void registerCommands() {
        if (mainConfig.getBoolean("MESSAGE-REPLY-BOOLEAN")) {
            Arrays.asList(new MessageCommand(),
                    new ReplyCommand(),
                    new PrivateMessageCommand())
                    .forEach(command -> registerCommand(command, getName()));
        }
        Arrays.asList(new StaffChatCommand(),
                new CoreReloadCommand(),
                new ServerMonitorCommand(),
                new ListCommand())
                .forEach(commands -> registerCommand(commands, getName()));
    }

    private void registerListeners() {
        Arrays.asList(new StaffListener(),
                new PlayerListener(),
                new MenuListener(),
                new ServerListener())
                .forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void loadTasks() {
        TaskUtil.runTimer(new VipsOnlineTask(), 2400L, 2400L);
    }

    public void loadVault() {
        RegisteredServiceProvider<Chat> provider = getServer().getServicesManager().getRegistration(Chat.class);
        if (provider != null) {
            chat = provider.getProvider();
        }
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

    private void registerCommand(Command cmd, String fallbackPrefix) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
    }

//    private void handlerNMS() {
//        String packageName = this.getServer().getClass().getPackage().getName();
//        String version = packageName.substring(packageName.lastIndexOf('.') + 1);
//        try {
//            final Class<?> clazz = Class.forName("net.pandamc.core.disguise.nms.impl." + version);
//            if (DisguiseImplementation.class.isAssignableFrom(clazz)) {
//                disguiseImplementation = (DisguiseImplementation) clazz.getConstructor().newInstance();
//            }
//            getLogger().info("Using -> " + version + " NMS");
//        } catch (final Exception e) {
//            getLogger().info("&4ERROR &c-> Could not find support for this version. Running version: " + version);
//            Bukkit.getPluginManager().disablePlugin(this);
//        }
//    }
}
