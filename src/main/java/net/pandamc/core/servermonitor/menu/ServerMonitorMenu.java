package net.pandamc.core.servermonitor.menu;

import net.pandamc.core.servermonitor.button.ServerMonitorButton;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerMonitorMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "&aServer Monitor";
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(getSlot(1, 1), new ServerMonitorButton("hub"));
        buttons.put(getSlot(2, 1), new ServerMonitorButton("practice"));
        buttons.put(getSlot(3, 1), new ServerMonitorButton("kitmap"));
        buttons.put(getSlot(4, 1), new ServerMonitorButton("squads"));
        buttons.put(getSlot(5, 1), new ServerMonitorButton("combo"));
        return buttons;
    }
}