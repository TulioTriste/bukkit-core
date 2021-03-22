package net.pandamc.core.database;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ryzeon
 * Project: rDisguise
 * Date: 18/01/2021 @ 10:58 p. m.
 */

@Getter
@Setter
public class DatabaseCredentials {

    private String host, database, user, password, authDatabase, table;
    private int port;
    private boolean auth;
    private Type type;

    public DatabaseCredentials(String host, int port, String database, boolean auth, String user, String password, String authDatabase) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.auth = auth;
        this.user = user;
        this.password = password;
        this.authDatabase = authDatabase;
        this.type = Type.MONGODB;
    }

    public DatabaseCredentials(String host, int port, String database, String table, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.table = table;
        this.user = user;
        this.password = password;
        this.type = Type.SQL;
    }

    public enum Type {
        MONGODB,
        SQL
    }
}
