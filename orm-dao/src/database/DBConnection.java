package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

public class DBConnection {
    private static String url = "jdbc:mysql://localhost:3306/mysql";
    private static String urlPrefix = "jdbc:mysql";
    private static String ip = "localhost";
    private static String port = "3306";
    private static String databaseName = "default";
    private static String username = "root";
    private static String password = "123456";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void setDefaultConnectConfig() {
    }


    public static void setConnectConfig(Properties properties) {
        String url = properties.getProperty("url");
        String urlPrefix = properties.getProperty("urlPrefix");
        String ip = properties.getProperty("ip");
        String port = properties.getProperty("port");
        String databaseName = properties.getProperty("databaseName");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        if (url != null) {
            DBConnection.url = url;
        } else {
            if (urlPrefix != null) DBConnection.urlPrefix = urlPrefix;
            if (ip != null) DBConnection.ip = ip;
            if (port != null) DBConnection.port = port;
            if (databaseName != null) DBConnection.databaseName = databaseName;
            DBConnection.url = DBConnection.urlPrefix + "://" + DBConnection.ip + ":" + DBConnection.port + "/" + DBConnection.databaseName;
        }
        if (username != null) DBConnection.username = username;
        if (password != null) DBConnection.password = password;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
