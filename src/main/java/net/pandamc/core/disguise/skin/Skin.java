package net.pandamc.core.disguise.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.pandamc.core.Core;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.net.URL;

import static net.pandamc.core.disguise.nms.DisguiseImplementation.ID_API;
import static  net.pandamc.core.disguise.nms.DisguiseImplementation.WEB_API;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 17/02/2021 @ 01:27
 */

@AllArgsConstructor
@Getter
@Setter
public class Skin {

    public static Skin DEFAULT_SKIN = new Skin(
            "ewogICJ0aW1lc3RhbXAiIDogMTYxMzYxNjYxNzA5MSwKICAicHJvZmlsZUlkIiA6ICJjZmYzYmRmMTc4MzY0OWUxYmIxOWM1ODU4Y2Y2ZjAzMCIsCiAgInByb2ZpbGVOYW1lIiA6ICJSeXplb24iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViODFmM2JmNGNjMDhjNGIyMDM4YzkwMGQ1YjMyNDAxYTliYzc5MzVhY2ZlZDZjNWQzNDk4YjU2NmJhMjBjMyIKICAgIH0KICB9Cn0=",
            "Ayhmn2wF75BjR339YvUQLFnRDZlK6E3Aj4ly6YmwYp98s/x5dS7LrBz5aGfRwf+4wX8lsv4DAirXsluWItrC6R6+a9vHHTZHhYDodncuaak56st00EppUT0m3/vCk2vUa00CFFOaiNqvjBqwmQ92uCPSz1ZM56dlD924KywjpUlzcNVI0sJOKqMhA9U4bPr3zP+RAhudzetnkfNbDf8h6PCSlbG8Jxexh5czCMaLlgbWPi1p8xBnFW8Bl5nqKUNNxAdM/PC7Ws+Zqk1Bldp9jkx7MZB3aI8Oo7bayEj3aNVXcwffXKMmcAsk0MPCGmc7Gca4EwjkGg6O2mXLD0KtoxaUhQnt9GYF76nLDhq01BrQBd+GRIWhUqwquqiDxFXy7ooAtcE19T0u8YDMPjuwP/LGIMnEChg42PRuoNPb3WO33EdV86/iW52+9YeOy5cUR0uJaa85jTWeCs/89uEIiCNRBDbEGHNK8eGa4DwKm5gqu1w57b52zWoCmJcTuvFzQ+Y65QAno2J+6IiPgrdUNs72md36RMWRxKe2SgaieWu7MnrDK1ud3ac6fv6ulFNiDr8GaSUBwbgm/ivIbwqc81wunM8TrLH0h6Bf/mUkafpM196bjJGCd5+VhnNgOPhuAH9hJg7GRr4/SXaFFXzqr7dx3WM78dYqLekJyI5BeXg=");

    private String value;
    private String signature;

    public Skin(Document document) {
        if (document.containsKey("value") && document.getString("value") != null) {
            this.value = document.getString("value");
        }
        if (document.containsKey("signature") && document.getString("signature") != null) {
            this.signature = document.getString("signature");
        }
    }

    public static Skin getSkinByName(String name) {
        String texture = null;
        String signature = null;

        try {
            String idChecker = ID_API + name;
            URL urlChecker = new URL(idChecker);

            InputStreamReader reader = new InputStreamReader(urlChecker.openStream());
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            String id = object.get("id").getAsString();

            String link = WEB_API + id + "?unsigned=false";
            urlChecker = new URL(link);
            reader = new InputStreamReader(urlChecker.openStream());
            JsonObject properties = new JsonParser().parse(reader).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            texture = properties.get("value").getAsString();
            signature = properties.get("signature").getAsString();
            reader.close();
        } catch (IllegalStateException ex) {
            return Skin.DEFAULT_SKIN;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Skin.DEFAULT_SKIN;
        }

        return new Skin(texture, signature);
    }

    public Document getDocumentToSave() {
        Document document = new Document();
        document.put("value", value);
        document.put("signature", signature);

        return document;
    }

    public static Skin getPlayerSkin(Player player) {
        return Core.get().getDisguiseImplementation().getPlayerSkin(player);
    }
}
