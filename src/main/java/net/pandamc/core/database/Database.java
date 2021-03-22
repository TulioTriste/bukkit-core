package net.pandamc.core.database;

import org.bson.Document;

import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 18/01/2021 @ 10:58 p. m.
 */

public interface Database {

    Document loadData(UUID uuid, String name);

    void saveData(Document document);

    void connect(DatabaseCredentials credentials);

    void close();
}
