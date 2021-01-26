package net.pandamc.core.servermonitor.menu;

import lombok.AllArgsConstructor;
import net.pandamc.core.servermonitor.button.RebootButton;
import net.pandamc.core.servermonitor.button.WhitelistButton;
import net.pandamc.core.util.menu.Button;
import net.pandamc.core.util.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ServerConfigurationMenu extends Menu {

    public String server;

    @Override
    public String getTitle(Player player) {
        return "&a" + server + " Server Configuration.";
    }

    @Override
    public int getSize() {
        return 9 * 3;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        buttons.put(getSlot(2,1), new RebootButton(server));
        buttons.put(getSlot(6,1), new WhitelistButton(server));
        return buttons;
    }
}
