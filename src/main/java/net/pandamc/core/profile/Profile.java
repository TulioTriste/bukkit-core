package net.pandamc.core.profile;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.pandamc.core.Core;
import net.pandamc.core.tags.Tag;
import net.pandamc.core.util.CC;
import net.pandamc.core.util.TaskUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@RequiredArgsConstructor
public class Profile {

    @Getter public static Map<UUID, Profile> profiles = Maps.newHashMap();
    @Getter private static MongoCollection<Document> collection;

    public final UUID uuid;

    public boolean online;
    public String name;
    public Tag tag;

    public void load() {
        Document document = collection.find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null) {
            this.save();
            return;
        }

        if (document.containsKey("name")) this.name = document.getString("name");

        if (document.containsKey("tag")) this.tag = Tag.getTag(document.getString("tag"));
    }

    public void save() {
        Document document = new Document();
        document.put("uuid", uuid.toString());

        if (online) document.put("name", Bukkit.getPlayer(uuid).getName());
        else document.put("name", name);

        if (tag != null) document.put("tag", tag.getName());

        collection.replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }

    public static void init() {
        collection = Core.get().getMongoDatabase().getCollection("profiles");

        TaskUtil.runLater(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Profile profile = new Profile(player.getUniqueId());

                try {
                    profile.load();
                } catch (Exception e) {
                    if (profile.isOnline()) {
                        player.kickPlayer(CC.RED + "The server is loading...");
                    }
                    continue;
                }

                profiles.put(player.getUniqueId(), profile);
            }
        },  40L);


        // Save every 5 minutes to prevent data loss
        TaskUtil.runTimerAsync(() -> profiles.forEach((uuid1, profile) -> profile.save()), 6000L, 6000L);
    }

    public static Profile get(UUID uuid) {
        return profiles.get(uuid);
    }
}
