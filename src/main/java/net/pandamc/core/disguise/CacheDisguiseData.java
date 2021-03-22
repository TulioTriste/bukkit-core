package net.pandamc.core.disguise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.pandamc.core.disguise.skin.Skin;
import org.bson.Document;

import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 14/02/2021 @ 04:09
 */

@AllArgsConstructor
@Getter
public class CacheDisguiseData {

    private UUID uuid;

    private String realName;
    @Setter private String disguiseName;

    @Setter private Skin realSkin;
    private Skin disguiseSkin;

    public Document getDocumentToSave() {
        Document document = new Document();
        document.put("uuid", uuid.toString());
        document.put("realName", realName);
        document.put("fakeName", disguiseName);
        document.put("realSkin", realSkin.getDocumentToSave());
        document.put("fakeSkin", disguiseSkin.getDocumentToSave());

        return document;
    }

    public CacheDisguiseData(Document document) {
        if (document != null) {
            if (document.containsKey("uuid") && document.getString("uuid") != null) {
                this.uuid = UUID.fromString(document.getString("uuid"));
            }
            if (document.containsKey("realName") && document.getString("realName") != null) {
                this.realName = document.getString("realName");
            }
            if (document.containsKey("fakeName") && document.getString("fakeName") != null) {
                this.disguiseName = document.getString("fakeName");
            }
            if (document.containsKey("realSkin") && document.get("realSkin") != null) {
                this.realSkin = new Skin((Document) document.get("realSkin"));
            }
            if (document.containsKey("fakeSkin") && document.get("fakeSkin") != null) {
                this.disguiseSkin = new Skin((Document) document.get("fakeSkin"));
            }
        }
    }
}
