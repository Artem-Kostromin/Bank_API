package ru.sberstart.repository.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.repository.CardRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CardRepositoryImpl implements CardRepository {
    private final Connection connection;
    private final AccountRepository accountRepository;

    @SneakyThrows
    @Override
    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM cards");
        ResultSet rs = prStatement.executeQuery();
        while(rs.next()) {
            cards.add(fetch(rs));
        }
        prStatement.close();
        return cards;
    }

    @SneakyThrows
    @Override
    public List<Card> findAllByAccount(long accountId) {
        List<Card> cards = new ArrayList<>();
        PreparedStatement prStatement = connection
                .prepareStatement("SELECT * FROM cards WHERE account_id = ?");
        prStatement.setLong(1, accountId);
        ResultSet rs = prStatement.executeQuery();
        while(rs.next()) {
            cards.add(fetch(rs));
        }
        prStatement.close();
        return cards;
    }

    @SneakyThrows
    @Override
    public Card findOne(long id) {
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM cards WHERE id = ?");
        prStatement.setLong(1, id);
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        Card card = fetch(rs);
        rs.close();
        prStatement.close();
        return card;
    }

    @SneakyThrows
    @Override
    public Card persist(long accountId, Card card) {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO cards values (default, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        prStatement.setLong(1, accountId);
        prStatement.setBigDecimal(2, BigDecimal.valueOf(0));
        prStatement.executeUpdate();
        ResultSet rs = prStatement.getGeneratedKeys();
        card.setAccountId(accountId);
        if(rs.next()) {
            long id = rs.getLong(1);
            card.setId(id);
        }
        prStatement.close();
        return card;
    }

    @SneakyThrows
    @Override
    public boolean removeOne(long id) {
        Card card = findOne(id);
        PreparedStatement prStatement = connection.prepareStatement("DELETE FROM cards WHERE id = ?");
        prStatement.setLong(1, id);
        int i = prStatement.executeUpdate();
        prStatement.close();
        accountRepository.findOne(id).getCards().remove(card);
        return i > 0;
    }

    @SneakyThrows
    @Override
    public BigDecimal checkBalance(long id) {
        PreparedStatement prStatement = connection.prepareStatement("SELECT balance FROM cards WHERE id = ?");
        prStatement.setLong(1, id);
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        BigDecimal balance = rs.getBigDecimal("balance");
        prStatement.close();
        return balance;
    }

    private Card fetch(ResultSet row) throws SQLException {
        if(row == null) return null;
        Card card = new Card();
        card.setId(row.getLong("id"));
        card.setAccountId(row.getLong("account_id"));
        card.setBalance(row.getBigDecimal("balance"));
        return card;
    }
}
