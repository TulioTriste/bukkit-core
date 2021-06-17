package net.pandamc.core.tags;

import net.pandamc.core.Core;
import net.pandamc.core.util.file.type.BasicConfigurationFile;

public class TagManager {

    public static Tag getTag(String name) {
        for (Tag tag : Tag.getTagStorage()) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }

        return null;
    }

    public static void openManager() {
        BasicConfigurationFile configuration = Core.get().getTagsConfig();

        for (String name : configuration.getConfiguration().getKeys(false)) {
            String type = configuration.getString(name + ".TYPE");
            String display = configuration.getString(name + ".DISPLAY");

            Tag tag = new Tag(name, display, TagType.valueOf(type));

            Tag.getTagStorage().add(tag);
        }

        System.out.println("[bukkit-core] Loaded " + Tag.getTagStorage().size() + " Tag.");
    }
}
