package ru.sberstart.repository;

import org.h2.tools.DeleteDbFiles;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.util.db.JdbcConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Objects;

public class TestAccountRepository {

    @Before
    public void before() {
        DeleteDbFiles.execute("~/Bank_API", "Bank_API", true);

        try {
            RunScript.execute(Objects.requireNonNull(JdbcConnection.getConnection()),
                    new FileReader("src/main/resources/testScript/initSchema.sql"));

        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

//    @Test
//    public void test
}
