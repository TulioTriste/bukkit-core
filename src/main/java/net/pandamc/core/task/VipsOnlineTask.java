package net.pandamc.core.task;

import lombok.var;
import net.pandamc.core.Core;
import net.pandamc.core.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class VipsOnlineTask implements Runnable {

    @Override
    public void run() {
        var vips = new ArrayList<Player>();
        Core.get().getServer().getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("bukkit.core.vip") && !player.isOp() && !player.hasPermission("*"))
                .forEach(vips::add);
        if (!vips.isEmpty()) {
            var message = new StringBuilder();
            vips.forEach(player -> message.append("&c").append(player.getName()).append("&7, "));
            Arrays.asList("",
                    " &d&lRagnar's &dconnected! &7(" + vips.size() + ")",
                    " &7\u25BA " + message.substring(0, (message.length() - 2)),
                    "")
                    .forEach(lines -> Bukkit.broadcastMessage(CC.translate(lines)));
        }
    }
}
