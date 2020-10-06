package ru.sberstart.repository;

import lombok.SneakyThrows;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ComparisonFailure;
import org.junit.Test;
import ru.sberstart.entity.Account;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.impl.CardRepositoryImpl;
import ru.sberstart.util.db.JdbcConnection;
import sun.java2d.pipe.AAShapePipe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class TestCardRepository {
    private final Connection connection = JdbcConnection.getConnection();
    private final CardRepositoryImpl cardRepo = new CardRepositoryImpl(connection);

    @Before
    public void before() {
        DeleteDbFiles.execute("~/Bank_API", "Bank_API", true);

        try {
            RunScript.execute(Objects.requireNonNull(connection),
                    new FileReader("src/main/resources/initTables.sql"));
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    @Test
    public void findAllTest(){
        int expectedNumberOfCards = 4;
        Assert.assertEquals(expectedNumberOfCards, cardRepo.findAll().size());

    }

    @SneakyThrows
    @Test
    public void findAllByAccountTest(){
        int numberOfCardInFirstAcc = 2;
        Assert.assertEquals(numberOfCardInFirstAcc, cardRepo.findAllByAccount(1).size());
    }

    @SneakyThrows
    @Test
    public void findOneTest(){
        long id = 3;
        Card card = cardRepo.findOne(id);
        Assert.assertEquals(id, card.getId());
    }

    @SneakyThrows
    @Test
    public void persistTest(){
        long id = cardRepo.persist(1, new Card()).getId();
        Assert.assertTrue(id > 0);
    }

    @SneakyThrows
    @Test
    public void removeTest(){
        Assert.assertTrue(cardRepo.remove(2));
    }

    @SneakyThrows
    @Test
    public void checkBalanceTest(){
        BigDecimal expectedBalance = BigDecimal.valueOf(500);
        Assert.assertEquals(expectedBalance, cardRepo.checkBalance(1));
    }

}
