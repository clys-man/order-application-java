package edu.unifor.clysman.database.connector;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Connector implements IConnector {
    protected final String host;
    protected final String port;
    protected final String database;
    protected final String user;
    protected final String password;

    public Connector(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    abstract public Connection getConnection() throws SQLException;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public String getPort() {
        return this.port;
    }

    @Override
    public String getDatabase() {
        return this.database;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
