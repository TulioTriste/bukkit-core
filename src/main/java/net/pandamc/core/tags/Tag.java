package net.pandamc.core.tags;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.pandamc.core.Core;
import net.pandamc.core.util.file.type.BasicConfigurationFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Tag {

    @Getter public static List<Tag> tagStorage = Lists.newArrayList();

    private final String name;
    private final String display;

    private final TagType type;

    public static void init() {
        BasicConfigurationFile configuration = Core.get().getTagsConfig();

        for (String name : configuration.getConfiguration().getKeys(false)) {
            String type = configuration.getString(name + ".TYPE");
            String display = configuration.getString(name + ".DISPLAY");

            Tag tag = new Tag(name, display, TagType.valueOf(type));

            Tag.getTagStorage().add(tag);
        }
        System.out.println("[bukkit-core] Loaded " + Tag.getTagStorage().size() + " Tag.");
    }

    public static Tag getTag(String name) {
        for (Tag tag : Tag.getTagStorage()) {
            if (tag.getName().equalsIgnoreCase(name)) {
                return tag;
            }
        }

        return null;
    }
}
