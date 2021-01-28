package com.lunarclient.bukkitapi;

import net.pandamc.core.Core;
import com.lunarclient.bukkitapi.event.LCPacketReceivedEvent;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.lunarclient.bukkitapi.event.LCPacketSentEvent;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketBossBar;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketGhost;
import com.lunarclient.bukkitapi.object.LCGhost;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketCooldown;
import com.lunarclient.bukkitapi.object.LCCooldown;
import com.lunarclient.bukkitapi.object.LCWaypoint;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTitle;
import java.time.Duration;
import com.lunarclient.bukkitapi.object.TitleType;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNametagsOverride;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramRemove;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologramUpdate;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketHologram;
import java.util.Arrays;
import org.bukkit.util.Vector;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketTeammates;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketServerRule;
import com.lunarclient.bukkitapi.nethandler.client.obj.ServerRule;
import com.lunarclient.bukkitapi.object.MinimapStatus;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketStaffModState;
import com.lunarclient.bukkitapi.object.StaffModule;
import com.lunarclient.bukkitapi.nethandler.client.obj.Emote;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketNotification;
import com.lunarclient.bukkitapi.object.LCNotification;

import java.util.stream.Collectors;
import org.bukkit.plugin.messaging.Messenger;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketUpdateWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerQuitEvent;
import com.lunarclient.bukkitapi.event.LCPlayerUnregisterEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.Event;
import com.lunarclient.bukkitapi.event.LCPlayerRegisterEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketEmoteBroadcast;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointRemove;
import com.lunarclient.bukkitapi.nethandler.shared.LCPacketWaypointAdd;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceChannelSwitch;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketVoiceMute;
import com.lunarclient.bukkitapi.nethandler.client.LCPacketClientVoice;
import com.lunarclient.bukkitapi.nethandler.server.LCPacketStaffModStatus;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import com.lunarclient.bukkitapi.nethandler.server.LCNetHandlerServer;
import com.lunarclient.bukkitapi.event.LCAntiCheatStatusEvent;
import org.bukkit.World;
import java.util.function.Function;
import com.lunarclient.bukkitapi.nethandler.LCPacket;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import org.bukkit.event.Listener;

public final class LunarClientAPI implements Listener
{
    private static final String MESSAGE_CHANNEL = "Lunar-Client";
    private static final String FROM_BUNGEE_CHANNEL = "LC|ACU";
    private static final boolean EMBED_PROTECTION_DATA_IN_LOGIN = false;
    private static String messageChannel;
    private static String fromBungeeChannel;
    private static LunarClientAPI instance;
    private final Set<UUID> playersRunningLunarClient;
    private final Set<UUID> playersRunningAntiCheat;
    private final Set<UUID> playersNotRegistered;
    private final Map<UUID, List<LCPacket>> packetQueue;
    private final Map<UUID, Function<World, String>> worldIdentifiers;
    private final Map<UUID, LCAntiCheatStatusEvent.Status> preJoinStatuses;
    private LCNetHandlerServer netHandlerServer;
    
    public LunarClientAPI(final Core plugin) {
        this.playersRunningLunarClient = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.playersRunningAntiCheat = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.playersNotRegistered = new HashSet<>();
        this.packetQueue = new HashMap<>();
        this.worldIdentifiers = new HashMap<>();
        this.netHandlerServer = new LCNetHandlerServer() {
            @Override
            public void handleStaffModStatus(final LCPacketStaffModStatus lcPacketStaffModStatus) {
            }
            
            @Override
            public void handleVoice(final LCPacketClientVoice lcPacketClientVoice) {
            }
            
            @Override
            public void handleVoiceMute(final LCPacketVoiceMute lcPacketVoiceMute) {
            }
            
            @Override
            public void handleVoiceChannelSwitch(final LCPacketVoiceChannelSwitch lcPacketVoiceChannelSwitch) {
            }
            
            @Override
            public void handleAddWaypoint(final LCPacketWaypointAdd lcPacketWaypointAdd) {
            }
            
            @Override
            public void handleRemoveWaypoint(final LCPacketWaypointRemove lcPacketWaypointRemove) {
            }
            
            @Override
            public void handleEmote(final LCPacketEmoteBroadcast lcPacketEmoteBroadcast) {
            }
        };
        this.preJoinStatuses = new HashMap<>();
        LunarClientAPI.instance = this;
        final Messenger messenger = plugin.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, getMessageChannel());
        messenger.registerIncomingPluginChannel(plugin, getMessageChannel(), (channel, player, bytes) -> {
            final LCPacket packet = LCPacket.handle(bytes, player);
            Bukkit.getPluginManager().callEvent(new LCPacketReceivedEvent(player, packet));
            packet.process(this.netHandlerServer);
        });
        messenger.registerIncomingPluginChannel(plugin, getFromBungeeChannel(), (channel, p, bytes) -> {
            final String[] payload = new String(bytes, StandardCharsets.UTF_8).split(":");
            final UUID uuid = UUID.fromString(payload[0]);
            final boolean prot = Boolean.parseBoolean(payload[1]);
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                this.anticheatUpdate(player, prot ? LCAntiCheatStatusEvent.Status.PROTECTED : LCAntiCheatStatusEvent.Status.UNPROTECTED);
            }
            else {
                this.preJoinStatuses.put(uuid, prot ? LCAntiCheatStatusEvent.Status.PROTECTED : LCAntiCheatStatusEvent.Status.UNPROTECTED);
            }
        });
        plugin.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.LOWEST)
            public void onPlayerJoin(final PlayerJoinEvent event) {
                if (LunarClientAPI.this.preJoinStatuses.containsKey(event.getPlayer().getUniqueId())) {
                    LunarClientAPI.this.anticheatUpdate(event.getPlayer(), LunarClientAPI.this.preJoinStatuses.remove(event.getPlayer().getUniqueId()));
                }
            }
            
            @EventHandler
            public void onPlayerLogin(final PlayerLoginEvent event) {
            }
            
            @EventHandler
            public void onRegister(final PlayerRegisterChannelEvent event) {
                if (!event.getChannel().equals(getMessageChannel())) {
                    return;
                }
                LunarClientAPI.this.playersNotRegistered.remove(event.getPlayer().getUniqueId());
                LunarClientAPI.this.playersRunningLunarClient.add(event.getPlayer().getUniqueId());
                if (LunarClientAPI.this.packetQueue.containsKey(event.getPlayer().getUniqueId())) {
                    LunarClientAPI.this.packetQueue.get(event.getPlayer().getUniqueId()).forEach(p -> LunarClientAPI.this.sendPacket(event.getPlayer(), p));
                    LunarClientAPI.this.packetQueue.remove(event.getPlayer().getUniqueId());
                }
                plugin.getServer().getPluginManager().callEvent(new LCPlayerRegisterEvent(event.getPlayer()));
                this.updateWorld(event.getPlayer());
            }
            
            @EventHandler
            public void onUnregister(final PlayerUnregisterChannelEvent event) {
                if (event.getChannel().equals(getMessageChannel())) {
                    LunarClientAPI.this.playersRunningLunarClient.remove(event.getPlayer().getUniqueId());
                    plugin.getServer().getPluginManager().callEvent(new LCPlayerUnregisterEvent(event.getPlayer()));
                }
            }
            
            @EventHandler
            public void onUnregister(final PlayerQuitEvent event) {
                LunarClientAPI.this.playersRunningLunarClient.remove(event.getPlayer().getUniqueId());
                LunarClientAPI.this.playersNotRegistered.remove(event.getPlayer().getUniqueId());
            }
            
            @EventHandler(priority = EventPriority.LOWEST)
            public void onJoin(final PlayerJoinEvent event) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    if (!LunarClientAPI.this.isRunningLunarClient(event.getPlayer())) {
                        LunarClientAPI.this.playersNotRegistered.add(event.getPlayer().getUniqueId());
                        LunarClientAPI.this.packetQueue.remove(event.getPlayer().getUniqueId());
                    }
                }, 40L);
            }
            
            @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onWorldChange(final PlayerChangedWorldEvent event) {
                this.updateWorld(event.getPlayer());
            }
            
            private void updateWorld(final Player player) {
                final String worldIdentifier = LunarClientAPI.this.getWorldIdentifier(player.getWorld());
                LunarClientAPI.this.sendPacket(player, new LCPacketUpdateWorld(worldIdentifier));
            }
        }, plugin);
    }
    
    private static String getMessageChannel() {
        if (LunarClientAPI.messageChannel == null) {
            String bukkitVersion = Bukkit.getServer().getBukkitVersion();
            final Version ver = new Version(bukkitVersion);
            if (ver.compareTo(new Version("1.13")) >= 0) {
                LunarClientAPI.messageChannel = "lunarclient:pm";
            }
            else {
                LunarClientAPI.messageChannel = "Lunar-Client";
            }
        }
        return LunarClientAPI.messageChannel;
    }
    
    private static String getFromBungeeChannel() {
        if (LunarClientAPI.fromBungeeChannel == null) {
            String bukkitVersion = Bukkit.getServer().getBukkitVersion();
            bukkitVersion = bukkitVersion.substring(0, bukkitVersion.indexOf(45));
            final Version ver = new Version(bukkitVersion);
            if (ver.compareTo(new Version("1.13")) >= 0) {
                LunarClientAPI.fromBungeeChannel = "lunarclient:acu";
            }
            else {
                LunarClientAPI.fromBungeeChannel = "LC|ACU";
            }
        }
        return LunarClientAPI.fromBungeeChannel;
    }
    
    public static LunarClientAPI getInstance() {
        return LunarClientAPI.instance;
    }
    
    public boolean isRunningAntiCheat(final Player player) {
        return this.isRunningAntiCheat(player.getUniqueId());
    }
    
    public boolean isRunningAntiCheat(final UUID playerUuid) {
        return this.playersRunningAntiCheat.contains(playerUuid);
    }
    
    public void anticheatUpdate(final Player player, final LCAntiCheatStatusEvent.Status status) {
        if (!this.playersRunningAntiCheat.contains(player.getUniqueId()) && status == LCAntiCheatStatusEvent.Status.PROTECTED) {
            this.playersRunningAntiCheat.add(player.getUniqueId());
            Bukkit.getPluginManager().callEvent(new LCAntiCheatStatusEvent(player, status));
        }
        else if (this.playersRunningAntiCheat.contains(player.getUniqueId()) && status == LCAntiCheatStatusEvent.Status.UNPROTECTED) {
            this.playersRunningAntiCheat.remove(player.getUniqueId());
            Bukkit.getPluginManager().callEvent(new LCAntiCheatStatusEvent(player, status));
        }
    }
    
    public boolean isRunningLunarClient(final Player player) {
        return this.isRunningLunarClient(player.getUniqueId());
    }
    
    public boolean isRunningLunarClient(final UUID playerUuid) {
        return this.playersRunningLunarClient.contains(playerUuid);
    }
    
    public Set<Player> getPlayersRunningAntiCheat() {
        return Collections.unmodifiableSet(this.playersRunningAntiCheat.stream().map(Bukkit::getPlayer).collect(Collectors.toSet()));
    }
    
    public Set<Player> getPlayersRunningLunarClient() {
        return Collections.unmodifiableSet(this.playersRunningLunarClient.stream().map(Bukkit::getPlayer).collect(Collectors.toSet()));
    }
    
    public void sendNotification(final Player player, final LCNotification notification) {
        this.sendPacket(player, new LCPacketNotification(notification.getMessage(), notification.getDurationMs(), notification.getLevel().name()));
    }
    
    public String getWorldIdentifier(final World world) {
        String worldIdentifier = world.getUID().toString();
        if (this.worldIdentifiers.containsKey(world.getUID())) {
            worldIdentifier = this.worldIdentifiers.get(world.getUID()).apply(world);
        }
        return worldIdentifier;
    }
    
    public void registerWorldIdentifier(final World world, final Function<World, String> identifier) {
        this.worldIdentifiers.put(world.getUID(), identifier);
    }
    
    public void sendEmote(final Player player, final Emote emote) {
        for (final Player all : Bukkit.getOnlinePlayers()) {
            this.sendPacket(all, new LCPacketEmoteBroadcast(player.getUniqueId(), emote.getEmoteId()));
        }
    }
    
    public void sendNotificationOrFallback(final Player player, final LCNotification notification, final Runnable fallback) {
        if (this.isRunningLunarClient(player)) {
            this.sendNotification(player, notification);
        }
        else {
            fallback.run();
        }
    }
    
    public void setStaffModuleState(final Player player, final StaffModule module, final boolean state) {
        this.sendPacket(player, new LCPacketStaffModState(module.name(), state));
    }
    
    public void setMinimapStatus(final Player player, final MinimapStatus status) {
        this.sendPacket(player, new LCPacketServerRule(ServerRule.MINIMAP_STATUS, status.name()));
    }
    
    public void setCompetitiveGame(final Player player, final boolean isCompetitive) {
        this.sendPacket(player, new LCPacketServerRule(ServerRule.COMPETITIVE_GAME, isCompetitive));
    }
    
    public void giveAllStaffModules(final Player player) {
        for (final StaffModule module : StaffModule.values()) {
            getInstance().setStaffModuleState(player, module, true);
        }
    }
    
    public void disableAllStaffModules(final Player player) {
        for (final StaffModule module : StaffModule.values()) {
            getInstance().setStaffModuleState(player, module, false);
        }
    }
    
    public void sendTeammates(final Player player, final LCPacketTeammates packet) {
        this.validatePlayers(player, packet);
        this.sendPacket(player, packet);
    }
    
    public void validatePlayers(final Player sendingTo, final LCPacketTeammates packet) {
        packet.getPlayers().entrySet().removeIf(entry -> Bukkit.getPlayer(entry.getKey()) != null && !Bukkit.getPlayer(entry.getKey()).getWorld().equals(sendingTo.getWorld()));
    }
    
    public void addHologram(final Player player, final UUID id, final Vector position, final String[] lines) {
        this.sendPacket(player, new LCPacketHologram(id, position.getX(), position.getY(), position.getZ(), Arrays.asList(lines)));
    }
    
    public void updateHologram(final Player player, final UUID id, final String[] lines) {
        this.sendPacket(player, new LCPacketHologramUpdate(id, Arrays.asList(lines)));
    }
    
    public void removeHologram(final Player player, final UUID id) {
        this.sendPacket(player, new LCPacketHologramRemove(id));
    }
    
    public void overrideNametag(final Player target, final List<String> nametag, final Player viewer) {
        this.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), nametag));
    }
    
    public void resetNametag(final Player target, final Player viewer) {
        this.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), null));
    }
    
    public void hideNametag(final Player target, final Player viewer) {
        this.sendPacket(viewer, new LCPacketNametagsOverride(target.getUniqueId(), Collections.emptyList()));
    }
    
    public void sendTitle(final Player player, final TitleType type, final String message, final Duration displayTime) {
        this.sendTitle(player, type, message, Duration.ofMillis(500L), displayTime, Duration.ofMillis(500L));
    }
    
    public void sendTitle(final Player player, final TitleType type, final String message, final float scale, final Duration displayTime) {
        this.sendTitle(player, type, message, scale, Duration.ofMillis(500L), displayTime, Duration.ofMillis(500L));
    }
    
    public void sendTitle(final Player player, final TitleType type, final String message, final Duration fadeInTime, final Duration displayTime, final Duration fadeOutTime) {
        this.sendTitle(player, type, message, 1.0f, fadeInTime, displayTime, fadeOutTime);
    }
    
    public void sendTitle(final Player player, final TitleType type, final String message, final float scale, final Duration fadeInTime, final Duration displayTime, final Duration fadeOutTime) {
        this.sendPacket(player, new LCPacketTitle(type.name().toLowerCase(), message, scale, displayTime.toMillis(), fadeInTime.toMillis(), fadeOutTime.toMillis()));
    }
    
    public void sendWaypoint(final Player player, final LCWaypoint waypoint) {
        this.sendPacket(player, new LCPacketWaypointAdd(waypoint.getName(), waypoint.getWorld(), waypoint.getColor(), waypoint.getX(), waypoint.getY(), waypoint.getZ(), waypoint.isForced(), waypoint.isVisible()));
    }
    
    public void removeWaypoint(final Player player, final LCWaypoint waypoint) {
        this.sendPacket(player, new LCPacketWaypointRemove(waypoint.getName(), waypoint.getWorld()));
    }
    
    public void sendCooldown(final Player player, final LCCooldown cooldown) {
        this.sendPacket(player, new LCPacketCooldown(cooldown.getMessage(), cooldown.getDurationMs(), cooldown.getIcon().getId()));
    }
    
    public void sendGhost(final Player player, final LCGhost ghost) {
        this.sendPacket(player, new LCPacketGhost(ghost.getGhostedPlayers(), ghost.getUnGhostedPlayers()));
    }
    
    public void clearCooldown(final Player player, final LCCooldown cooldown) {
        this.sendPacket(player, new LCPacketCooldown(cooldown.getMessage(), 0L, cooldown.getIcon().getId()));
    }
    
    public void setBossbar(final Player player, final String text, final float health) {
        this.sendPacket(player, new LCPacketBossBar(0, text, health));
    }
    
    public void unsetBossbar(final Player player) {
        this.sendPacket(player, new LCPacketBossBar(1, null, 0.0f));
    }
    
    public boolean sendPacket(final Player player, final LCPacket packet) {
        if (this.isRunningLunarClient(player)) {
            player.sendPluginMessage(Core.get(), getMessageChannel(), LCPacket.getPacketData(packet));
            Bukkit.getPluginManager().callEvent(new LCPacketSentEvent(player, packet));
            return true;
        }
        if (!this.playersNotRegistered.contains(player.getUniqueId())) {
            this.packetQueue.putIfAbsent(player.getUniqueId(), new ArrayList<>());
            this.packetQueue.get(player.getUniqueId()).add(packet);
            return false;
        }
        return false;
    }
    
    public void setNetHandlerServer(final LCNetHandlerServer netHandlerServer) {
        this.netHandlerServer = netHandlerServer;
    }
    
    static {
        LunarClientAPI.messageChannel = null;
        LunarClientAPI.fromBungeeChannel = null;
    }
}
