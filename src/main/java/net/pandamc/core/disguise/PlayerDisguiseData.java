package net.pandamc.core.disguise;

import lombok.Getter;
import org.bson.Document;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:01
 */

@Getter
public class PlayerDisguiseData {

    private CacheDisguiseData cacheDisguiseData;

    private long disconnectDate;

    public PlayerDisguiseData(CacheDisguiseData cacheDisguiseData) {
        this.cacheDisguiseData = cacheDisguiseData;
    }

    public boolean canReDisguise() {
        long time = System.currentTimeMillis() - disconnectDate;
        return time < (10 * 1000);
    }

    public PlayerDisguiseData(Document document) {
        if (document.containsKey("disconnectDate") && document.getLong("disconnectDate") != null) {
            this.disconnectDate = document.getLong("disconnectDate");
        }
        if (document.containsKey("disguiseData") && document.get("disguiseData") != null) {
            this.cacheDisguiseData = new CacheDisguiseData((Document) document.get("disguiseData"));
        }
    }

    public Document getDocumentToSave() {
        Document document = new Document();
        document.put("disconnectDate", System.currentTimeMillis());
        document.put("disguiseData", cacheDisguiseData.getDocumentToSave());

        return document;
    }
}
