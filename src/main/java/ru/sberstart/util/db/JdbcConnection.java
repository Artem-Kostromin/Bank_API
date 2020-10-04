package ru.sberstart.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:~/Bank_API", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
