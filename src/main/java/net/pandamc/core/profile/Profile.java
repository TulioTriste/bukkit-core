package net.pandamc.core.profile;

import lombok.Getter;
import lombok.Setter;
import net.pandamc.core.Core;
import net.pandamc.core.disguise.PlayerDisguiseData;
import net.pandamc.core.util.TaskUtil;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Profile {

    @Getter private static final Map<UUID, Profile> profiles = new HashMap<>();

    private final UUID uuid;
    private String name;
    private Player player;
    @Setter private PlayerDisguiseData playerDisguiseData;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        if (!profiles.containsKey(uuid))
            TaskUtil.runAsync(() -> loadData(Core.get().getMongoManager().findData(uuid), true));
    }

    public Profile(UUID uuid, String name, Player player) {
        this.uuid = uuid;
        this.name = name;
        this.player = player;
    }

    /**
     * @param document
     * @param message
     */
    public void loadData(Document document, boolean message) {
        if (document.containsKey("playerDisguiseData")) {
            if (document.get("playerDisguiseData") != null) {
                this.playerDisguiseData = new PlayerDisguiseData((Document) document.get("playerDisguiseData"));
            }
        }

        if (message) {
            Bukkit.getLogger().info(this.name + "'s data successfully loaded.");
        }
    }

    /**
     * @param destroy
     * @param async
     */
    public void save(boolean destroy, boolean async) {
        Document document = new Document();

        // Identifier
        document.put("uuid", this.uuid.toString());
        document.put("name", this.name);
        document.put("name_toLowerCase", this.name.toLowerCase());

        document.put("playerDisguiseData", playerDisguiseData == null ? null : playerDisguiseData.getDocumentToSave());

        if (async) {
            TaskUtil.runAsync(() -> Core.get().getMongoManager().saveData(this.uuid, document));
        } else {
            Core.get().getMongoManager().saveData(this.uuid, document);
        }
        if (destroy) {
            profiles.remove(this.uuid);
        }
    }

    public boolean isDisguise() {
        return this.playerDisguiseData != null;
    }

    public static Profile get(UUID uuid) {
        return profiles.getOrDefault(uuid, new Profile(uuid));
    }
}
