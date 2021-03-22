package net.pandamc.core.database.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import net.pandamc.core.Core;
import net.pandamc.core.util.file.type.BasicConfigurationFile;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 18/01/2021 @ 10:58 p. m.
 */

@Getter
public class MongoHandler {

    private final BasicConfigurationFile mongoConfig = Core.get().getMainConfig();
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private MongoCollection<Document> playerData;

    public MongoHandler() {
        Bukkit.getLogger().info("Register MongoDB handler");
    }

    public List<Document> getCollectionDocument(MongoCollection<Document> documentMongoCollection) {
        return documentMongoCollection.find().into(new ArrayList<>());
    }

    public void connect() {
        try {
            Bukkit.getLogger().info("[Database] Connecting to database");
            if (this.mongoConfig.getBoolean("MONGO.AUTH.ENABLED")) {
                MongoCredential mongoCredential = MongoCredential.createCredential(
                        this.mongoConfig.getString("MONGO.AUTH.USERNAME"),
                        this.mongoConfig.getString("MONGO.AUTH.AUTH-DATABASE"),
                        this.mongoConfig.getString("MONGO.AUTH.PASSWORD").toCharArray());
                this.mongoClient = new MongoClient(
                        new ServerAddress(this.mongoConfig.getString("MONGO.HOST"), this.mongoConfig.getInteger("MONGO.PORT")),
                        Collections.singletonList(mongoCredential)
                );
            } else {
                this.mongoClient = new MongoClient(this.mongoConfig.getString("MONGO.HOST"), this.mongoConfig.getInteger("MONGO.PORT"));
            }
            this.mongoDatabase = this.mongoClient.getDatabase(this.mongoConfig.getString("MONGO.DATABASE"));
            this.playerData = this.mongoDatabase.getCollection("rDisguise-PlayerData");
            Bukkit.getLogger().info("[Database] Successfully handler 'rDisguise-PlayerData' collection.");
            Bukkit.getLogger().info("[Database] Connection successfully");
        } catch (Exception e) {
            Bukkit.getLogger().info("[Database] Disabling rDisguise, check your mongo.");
            Bukkit.getPluginManager().disablePlugin(Core.get());
        }
    }

    public boolean hasData(UUID identifier) {
        return this.findData(identifier) != null;
    }

    public boolean hasData(String name) {
        return this.findData(name) != null;
    }

    public Document findData(String name) {
        if (this.playerData.find(Filters.eq("name", name)).first() != null)
            return this.playerData.find(Filters.eq("name", name)).first();
        return this.playerData.find(Filters.eq("name_toLowerCase", name.toLowerCase())).first();
    }

    public Document findData(UUID identifier) {
        if (this.playerData.find(Filters.eq("uuid", identifier.toString())).first() != null) {
            return this.playerData.find(Filters.eq("uuid", identifier.toString())).first();
        }
        return this.findData(Bukkit.getOfflinePlayer(identifier).getName());
    }

    public void saveData(UUID identifier, Document document) {
        this.playerData.replaceOne(Filters.eq("uuid", identifier.toString()), document, (new UpdateOptions()).upsert(true));
    }

    public void saveDisguiseData(String identifier, Document document) {
        this.playerData.replaceOne(Filters.eq("name", identifier), document, (new UpdateOptions()).upsert(true));
    }
}
