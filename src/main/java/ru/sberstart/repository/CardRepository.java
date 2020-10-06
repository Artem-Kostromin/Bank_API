package ru.sberstart.repository;

import ru.sberstart.entity.Card;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CardRepository {

    List<Card> findAll() throws SQLException;

    List<Card> findAllByAccount(long accountId) throws SQLException;

    Card findOne(long id) throws SQLException;

    Card persist(long accountId, Card card) throws SQLException;

    boolean remove(long id) throws  SQLException;

    BigDecimal checkBalance(long id) throws SQLException;
}
