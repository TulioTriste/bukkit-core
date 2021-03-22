package net.pandamc.core.commands;

import net.pandamc.core.profile.Profile;
import net.pandamc.core.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:15
 */

public class DisguiseListCommand extends Command {

    public DisguiseListCommand() {
        super("disguiselist");
        setAliases(Arrays.asList("dlist"));
        setDescription("rdisguise.staff");
    }

    private String getFormat(List<Profile> data) {
        List<String> text = new ArrayList<>();
        String format = "&a<disguise_realName> &d-> &f<disguise_name>";
        data.forEach(playerData -> {
            text.add(CC.translate(format
                    .replace("<disguise_realName>", playerData.getPlayerDisguiseData().getCacheDisguiseData().getRealName())
                    .replace("<disguise_name>", playerData.getPlayerDisguiseData().getCacheDisguiseData().getDisguiseName())
            ));
        });
        return Arrays.toString(text.toArray()) + "\n";
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        List<Profile> disguiseList = Bukkit.getOnlinePlayers().stream().map(player -> Profile.get(player.getUniqueId()))
                .filter(Profile::isDisguise).collect(Collectors.toList());
        if (disguiseList.isEmpty()) {
            commandSender.sendMessage(CC.translate("&cNo available users in disguise."));
            return true;
        }
        commandSender.sendMessage(CC.translate("&7&m-------------------------"));
        commandSender.sendMessage(CC.translate("&d&oCurrent Disguise"));
        commandSender.sendMessage(CC.translate(""));
        commandSender.sendMessage(CC.translate(getFormat(disguiseList)));
        commandSender.sendMessage(CC.translate(""));
        commandSender.sendMessage(CC.translate("&7&m-------------------------"));
        return false;
    }
}
