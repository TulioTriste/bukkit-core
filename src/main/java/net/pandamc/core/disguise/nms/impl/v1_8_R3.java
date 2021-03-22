package net.pandamc.core.disguise.nms.impl;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.pandamc.core.Core;
import net.pandamc.core.disguise.CacheDisguiseData;
import net.pandamc.core.disguise.PlayerDisguiseData;
import net.pandamc.core.disguise.nms.DisguiseImplementation;
import net.pandamc.core.disguise.skin.Skin;
import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:12
 */

public class v1_8_R3 extends DisguiseImplementation {

    @Override
    public void applyDisguise(Profile data, CacheDisguiseData cacheDisguiseData, boolean reDisguise) {
        cacheDisguiseData.setRealSkin(Skin.getPlayerSkin(data.getPlayer()));
        data.setPlayerDisguiseData(new PlayerDisguiseData(cacheDisguiseData));

        Player player = data.getPlayer();
        CraftPlayer craftPlayer =((CraftPlayer)data.getPlayer());
        GameProfile profile = craftPlayer.getProfile();
        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", new Property("textures", cacheDisguiseData.getDisguiseSkin().getValue(), cacheDisguiseData.getDisguiseSkin().getSignature()));

        try {
            Field name = profile.getClass().getDeclaredField("name");
            name.setAccessible(true);
            name.set(profile, cacheDisguiseData.getDisguiseName());
        } catch(Exception ex){
            ex.printStackTrace();
        }

        player.setPlayerListName(cacheDisguiseData.getDisguiseName());

        TaskUtil.runLater(() -> {
             Bukkit.getOnlinePlayers().forEach(x -> {
                x.hidePlayer(player);
            });
        }, 2);

        TaskUtil.runLater(() -> {
             Bukkit.getOnlinePlayers().forEach(x -> {
                x.showPlayer(player);
            });
            Core.get().getDisguiseActions().forEach(disguiseAction -> disguiseAction.afterUnDisguise(player));
        }, 2);
        disguiseMessage(player, cacheDisguiseData, reDisguise);
    }

    @Override
    public void applyUnDisguise(Profile data, CacheDisguiseData cacheDisguiseData) {
        if (!data.isDisguise()) return;

        Player player = data.getPlayer();

        GameProfile gameProfile = ((CraftPlayer)player).getProfile();
        try {
            Field name = gameProfile.getClass().getDeclaredField("name");
            name.setAccessible(true);
            name.set(gameProfile, cacheDisguiseData.getRealName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.setPlayerListName(cacheDisguiseData.getRealName());

        TaskUtil.runLater(() -> {
            Bukkit.getOnlinePlayers().forEach(x -> {
                x.hidePlayer(player);
            });
        }, 3);

        gameProfile.getProperties().removeAll("textures");
        gameProfile.getProperties().put("textures", new Property("textures", cacheDisguiseData.getRealSkin().getValue(), cacheDisguiseData.getRealSkin().getSignature()));

        TaskUtil.runLater(() -> {
             Bukkit.getOnlinePlayers().forEach(x -> {
                x.showPlayer(player);
            });
            Core.get().getDisguiseActions().forEach(disguiseAction -> disguiseAction.afterUnDisguise(player));
        }, 3);
        data.setPlayerDisguiseData(null);
        unDisguiseMessage(player);
    }

    @Override
    public Skin getPlayerSkin(Player player) {
        String value = "";
        String signature = "";
        GameProfile gameProfile = getEntity(player).getProfile();

        for (Property property : gameProfile.getProperties().get("textures")) {
            value = property.getValue();
            signature = property.getSignature();
        }
        return new Skin(value, signature);
    }

    public EntityPlayer getEntity(Player player) {
        return ((CraftPlayer) player).getHandle();
    }
}
