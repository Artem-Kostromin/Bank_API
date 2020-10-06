package ru.sberstart.service;

import ru.sberstart.entity.Card;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {
    List<Card> findAll();

    List<Card> findAllByAccount(long accountId);

    Card findOne(long id);

    Card persist(long accountId, Card card);

    boolean removeOne(long id);

    BigDecimal checkBalance(long id);
}
