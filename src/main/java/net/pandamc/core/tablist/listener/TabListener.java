package net.pandamc.core.tablist.listener;

import lombok.AllArgsConstructor;
import net.pandamc.core.events.DisguiseUpdateEvent;
import net.pandamc.core.tablist.Tablist;
import net.pandamc.core.tablist.entry.TabEntry;
import net.pandamc.core.tablist.layout.TabLayout;
import net.pandamc.core.tablist.skin.Skin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class TabListener implements Listener {

	private final Tablist instance;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		TabLayout layout = new TabLayout(instance, player);
		layout.create();
		layout.setHeaderAndFooter();

		TabLayout.getLayoutMapping().put(player.getUniqueId(), layout);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		TabLayout.getLayoutMapping().remove(player.getUniqueId());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		TabLayout.getLayoutMapping().remove(player.getUniqueId());
	}
}
