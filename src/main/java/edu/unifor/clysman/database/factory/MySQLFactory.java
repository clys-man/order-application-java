package edu.unifor.clysman.database.factory;

import edu.unifor.clysman.database.connector.Connector;
import edu.unifor.clysman.database.connector.MySQLConnector;

public class MySQLFactory {
    public static Connector getConnector() {
        return new MySQLConnector("0.0.0.0", "3306", "av3", "root", "12341234");
    }
}
