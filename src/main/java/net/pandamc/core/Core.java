package net.pandamc.core;

import com.lunarclient.bukkitapi.LunarClientAPI;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.pandamc.core.commands.*;
import net.pandamc.core.listeners.PlayerListener;
import net.pandamc.core.listeners.StaffListener;
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
import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
@Setter
public class Core extends JavaPlugin {

    private BasicConfigurationFile mainConfig;
    private Redis redisManager;
    private String serverName;
    private Chat chat;
    private RankManager rankManager;
    private String rankSystem;

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
        redisManager.disconnect();
    }

    private void registerConfigs() {
        this.mainConfig = new BasicConfigurationFile(this, "config");
    }

    private void registerManagers() {
        new LunarClientAPI(this);
        redisManager = new Redis();
        Rank.init();
        redisManager.connect();
        this.serverName = getMainConfig().getString("SERVER");
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
                new ServerMonitorCommand())
                .forEach(commands -> registerCommand(commands, getName()));
    }

    private void registerListeners() {
        Arrays.asList(new StaffListener(),
                new PlayerListener(),
                new MenuListener())
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
}
