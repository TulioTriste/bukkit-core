package net.pandamc.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:06
 */

@Getter
@AllArgsConstructor
public class DisguiseEvent extends Event implements Cancellable {

    private final Player player;

    @Setter private boolean cancelled;

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        return this instanceof Cancellable && ((Cancellable) this).isCancelled();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
