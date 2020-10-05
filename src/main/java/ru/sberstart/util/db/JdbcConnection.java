package ru.sberstart.util.db;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnection {
    private final static Properties properties = new Properties();

    @SneakyThrows
    public static Connection getConnection() {
        FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
        properties.load(fis);

        String name = (String) properties.get("datasource.name");
        String url = (String) properties.get("datasource.url");
        String user = (String) properties.get("datasource.user");
        String password = (String) properties.get("datasource.password");

        try {
            Class.forName(name);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
