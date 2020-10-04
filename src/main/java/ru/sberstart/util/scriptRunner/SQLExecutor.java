package ru.sberstart.util.scriptRunner;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLExecutor{
    public static void runScript(Connection connection){
        try {
            RunScript.execute(connection, new FileReader("resources/initTables.sql"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
