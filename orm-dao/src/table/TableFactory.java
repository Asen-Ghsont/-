package table;

import database.DBConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class TableFactory {
    private static boolean isSetConnectConfig = false;

    public static void setDefaultConfig() {
        isSetConnectConfig = true;
        DBConnection.setDefaultConnectConfig();
    }

    public static void setConnectConfig(File connectConfigFile) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(connectConfigFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setConnectConfig(properties);
    }

    public static void setConnectConfig(Properties connectConfigProperties) {
        isSetConnectConfig = true;
        DBConnection.setConnectConfig(connectConfigProperties);
    }

    public static <T> Table<T> registerTable(Class<T> clazz) {
        if (!isSetConnectConfig)
            throw new RuntimeException("Have not configured connect information, plz set connect configure first.");
        return new Table<>(clazz);
    }
}
