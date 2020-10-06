package ru.sberstart.repository;

import org.h2.tools.DeleteDbFiles;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.util.db.JdbcConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TestAccountRepository {
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository repo = new AccountRepositoryImpl(connection);

    @Before
    public void before() {
        DeleteDbFiles.execute("~/Bank_API", "Bank_API", true);

        try {
            RunScript.execute(Objects.requireNonNull(JdbcConnection.getConnection()),
                    new FileReader("src/main/resources/initTables.sql"));
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void findOneTest() throws SQLException {
        long id = 3;
        Account account = repo.findOne(id);
        Assert.assertEquals(account.getId(), id);
    }

    @Test
    public void findAllTest() throws SQLException {
        List<Account> accounts = repo.findAll();
        Assert.assertTrue(accounts.size() > 0);
    }

    @Test
    public void persistTest() throws SQLException {
        long id = repo.persist(new Account()).getId();
        Assert.assertTrue(id > 0);
    }

    @Test
    public void removeOneTest() throws SQLException {
        Assert.assertTrue(repo.removeOne(2));
    }
}
