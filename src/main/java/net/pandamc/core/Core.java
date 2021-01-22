package net.pandamc.core;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.pandamc.core.commands.CoreReloadCommand;
import net.pandamc.core.commands.StaffChatCommand;
import net.pandamc.core.listeners.PlayerListener;
import net.pandamc.core.listeners.StaffListener;
import net.pandamc.core.util.file.type.BasicConfigurationFile;
import net.pandamc.core.util.redis.Redis;
import org.bukkit.command.Command;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter public class Core extends JavaPlugin {

    private BasicConfigurationFile mainConfig;
    private Redis redisManager;
    private String serverName;
    private Chat chat;

    @Override
    public void onEnable() {
        registerConfigs();
        registerManagers();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        redisManager.disconnect();
    }

    private void registerConfigs() {
        this.mainConfig = new BasicConfigurationFile(this, "config");
    }

    private void registerManagers() {
        loadVault();
        redisManager = new Redis();
        redisManager.connect();
        this.serverName = getMainConfig().getString("SERVER");
    }

    private void registerCommands() {
        Arrays.asList(new StaffChatCommand(),
                new CoreReloadCommand()).forEach(commands ->
                registerCommand(commands, getName()));
    }

    private void registerListeners() {
        Arrays.asList(new StaffListener(),
                new PlayerListener()).forEach(listener ->
                        getServer().getPluginManager().registerEvents(listener, this));
    }

    private void loadVault() {
        RegisteredServiceProvider<Chat> provider = getServer().getServicesManager().getRegistration(Chat.class);
        if (provider != null) {
            chat = provider.getProvider();
        }
    }

    private void registerCommand(Command cmd, String fallbackPrefix) {
        MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
    }

    public static Core get() {
        return getPlugin(Core.class);
    }
}
