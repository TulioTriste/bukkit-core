package net.pandamc.core.disguise;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import net.pandamc.core.Core;
import net.pandamc.core.database.impl.MongoHandler;
import net.pandamc.core.disguise.nms.DisguiseImplementation;
import net.pandamc.core.disguise.skin.Skin;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.file.type.BasicConfigurationFile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 17/02/2021 @ 01:36
 */

public class DisguiseManager {

    private final BasicConfigurationFile config = Core.get().getDisguiseConfig();

    private final MongoHandler mongoHandler = Core.get().getMongoManager();

    @Getter
    private final List<String> disguiseAvailableNames = new ArrayList<>();

    @Getter
    private final List<String> cacheDataNames = new ArrayList<>();
    protected final Map<String, Skin> disguiseSkinMap = new HashMap<>();

    protected final DisguiseImplementation disguiseImplementation = Core.get().getDisguiseImplementation();

    public void load() {
        disguiseAvailableNames.addAll(config.getConfiguration().getStringList("DISGUISE.NAMES"));
        Bukkit.getLogger().info("Loading... " + disguiseAvailableNames.size() + "names has been loaded.");

        config.getStringList("DISGUISE.SKINS").forEach(skin -> {
            Skin skinValue = Skin.getSkinByName(skin);
            if (skinValue == null) return;
            disguiseSkinMap.putIfAbsent(skin, skinValue);
        });
        Bukkit.getLogger().info("Loading... " + disguiseSkinMap.size() + " skins has been loaded.");
    }

    public void forceUnDisguise(UUID uuid, CacheDisguiseData data) {
        cacheDataNames.add(data.getDisguiseName());
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    if (!disguiseAvailableNames.contains(data.getDisguiseName())) {
                        disguiseAvailableNames.add(data.getDisguiseName());
                        cacheDataNames.remove(data.getDisguiseName());
                    }
                }
            }
        }.runTaskLater(Core.get(), 20 * 5);
    }

    public Skin getRandomSkin() {
        if (disguiseSkinMap.size() <= 1) {
            return Skin.DEFAULT_SKIN;
        }
        Random random = new Random();
        return new ArrayList<>(disguiseSkinMap.values()).get(random.nextInt(disguiseSkinMap.size() - 1));
    }

    public String getRandomName() {
        if (disguiseAvailableNames.isEmpty()) return null;
        Random random = new Random();
        int number = random.nextInt(disguiseAvailableNames.size() - 1);
        return disguiseAvailableNames.get(number);
    }

    public void handlerDisguise(Profile data, CacheDisguiseData cacheDisguiseData) {
        this.handlerDisguise(data, cacheDisguiseData, false);
    }

    public void handlerDisguise(Profile data, CacheDisguiseData cacheDisguiseData, boolean reDisguise) {
        disguiseImplementation.applyDisguise(data, cacheDisguiseData, reDisguise);
        disguiseAvailableNames.remove(cacheDisguiseData.getDisguiseName());
    }

    public void handlerUnDisguise(Profile data) {
        CacheDisguiseData cacheDisguiseData = data.getPlayerDisguiseData().getCacheDisguiseData();
        disguiseAvailableNames.add(cacheDisguiseData.getDisguiseName());
        disguiseImplementation.applyUnDisguise(data, cacheDisguiseData);
    }
}
