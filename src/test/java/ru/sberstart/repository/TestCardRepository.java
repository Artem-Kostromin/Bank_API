package ru.sberstart.repository;

import org.h2.tools.DeleteDbFiles;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.impl.AccountRepositoryImpl;
import ru.sberstart.repository.impl.CardRepositoryImpl;
import ru.sberstart.util.db.JdbcConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class TestCardRepository {
    private final Connection connection = JdbcConnection.getConnection();
    private final AccountRepository accountRepo = new AccountRepositoryImpl(connection);
    private final CardRepository cardRepo = new CardRepositoryImpl(connection, accountRepo);

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
    public void findAllTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards VALUES (default , 1, 500), (default , 1, 700)" +
                ", (default , 1, 700), (default , 1, 700)");
        prStatement.executeUpdate();
        int expectedNumberOfCards = 4;
        Assert.assertEquals(expectedNumberOfCards, cardRepo.findAll().size());
    }

    @Test
    public void findAllByAccountTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards VALUES (1, 1, 500), (2, 1, 700)");
        prStatement.executeUpdate();
        int expectedNumber = 2;
        Assert.assertEquals(expectedNumber, cardRepo.findAllByAccount(1).size());
    }

    @Test
    public void findOneTest() throws SQLException {
        Card expectedCard = new Card(1, 1, BigDecimal.valueOf(500));
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards VALUES (1, 1, 500)");
        prStatement.executeUpdate();
        Assert.assertEquals(expectedCard, cardRepo.findOne(1));
    }

    @Test
    public void persistTest() throws SQLException {
        Card exCard = new Card();
        exCard.setAccountId(1);
        exCard.setBalance(new BigDecimal("827.89"));
        exCard.setId(cardRepo.persist(exCard.getAccountId(), exCard).getId());

        assert connection != null;
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM cards WHERE id = ?");
        prStatement.setLong(1, exCard.getId());
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        Card card = new Card();
        card.setId(rs.getLong("id"));
        card.setAccountId(rs.getLong("account_id"));
        card.setBalance(rs.getBigDecimal("balance"));
        System.out.println(exCard.toString() + " " + card.toString());
        Assert.assertEquals(exCard, card);
    }

    @Test
    public void removeTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards VALUES (1, 1, 500), (2, 1, 600)");
        prStatement.executeUpdate();
        cardRepo.removeOne(1);
        prStatement = connection.prepareStatement("Select Count(id) as c From cards");
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        int count = rs.getInt("c");
        Assert.assertEquals(1, count);
    }

    @Test
    public void checkBalanceTest() throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards VALUES (1, 1, 500)");
        prStatement.executeUpdate();
        BigDecimal expectedBalance = BigDecimal.valueOf(500);
        Assert.assertEquals(expectedBalance, cardRepo.checkBalance(1));
    }

    @Test
    public void updateCheck() throws SQLException {
        PreparedStatement prStatement = null;
        assert connection != null;
        Card card1 = new Card();
        card1.setAccountId(2);
        card1.setBalance(new BigDecimal(200));
        Card card = cardRepo.persist(1,card1);
        long id = card.getId();
        Card expectedCard = cardRepo.update(id, card);
        prStatement = connection.prepareStatement("Select * From cards Where id = ?");
        prStatement.setLong(1, id);
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        Assert.assertEquals(expectedCard.getBalance(), rs.getBigDecimal("balance"));

    }

}
