package net.pandamc.core.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Tag {

    @Getter public static List<Tag> tagStorage = Lists.newArrayList();
    @Getter public static Map<UUID, Tag> userData = Maps.newHashMap();

    private final String name;
    private final String display;

    private final TagType type;

    public static void init() {

    }
}
