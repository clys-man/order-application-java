package edu.unifor.clysman.database.connector;

public interface IConnector {
    String getHost();
    String getPort();
    String getDatabase();
    String getUser();
    String getPassword();
}
