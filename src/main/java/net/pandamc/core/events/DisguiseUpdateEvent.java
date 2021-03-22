package net.pandamc.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@AllArgsConstructor
public class DisguiseUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Getter private final Player oldPlayer;
    @Getter private final Player player;
    @Getter private final boolean reset;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
