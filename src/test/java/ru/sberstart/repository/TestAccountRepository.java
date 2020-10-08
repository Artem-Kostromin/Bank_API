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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TestAccountRepository {
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository accountRepo = new AccountRepositoryImpl(connection);

    @Before
    public void before() {
        DeleteDbFiles.execute("~/Bank_API", "Bank_API", true);
        try {
            RunScript.execute(Objects.requireNonNull(connection),
                    new FileReader("src/main/resources/testScript/initSchema.sql"));
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void findOneTest() throws SQLException {
        Account expectedAccount = new Account();
        expectedAccount.setId(1);
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO accounts VALUES (1)");
        prStatement.executeUpdate();
        Assert.assertEquals(expectedAccount, accountRepo.findOne(1));
    }

    @Test
    public void findAllTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO accounts VALUES (1), (2), (3), (4)");
        prStatement.executeUpdate();
        int expectedNumberOfCards = 4;
        Assert.assertEquals(expectedNumberOfCards, accountRepo.findAll().size());
    }

    @Test
    public void persistTest() throws SQLException {
        Account account = accountRepo.persist(new Account());
        Assert.assertNotEquals(0, account.getId());
    }

    @Test
    public void removeOneTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO accounts VALUES (1), (2)");
        prStatement.executeUpdate();
        accountRepo.removeOne(1);
        prStatement = connection.prepareStatement("Select Count(id) as c From accounts");
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        int count = rs.getInt("c");
        Assert.assertEquals(1, count);
    }
}
