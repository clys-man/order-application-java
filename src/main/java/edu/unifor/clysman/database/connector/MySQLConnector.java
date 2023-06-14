package edu.unifor.clysman.database.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector extends Connector {
    public MySQLConnector(String host, String port, String database, String user, String password) {
        super(host, port, database, user, password);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection(
                        "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                        this.user,
                        this.password
                );
    }
}
