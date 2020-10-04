package ru.sberstart.repository;

import ru.sberstart.entity.Card;

import java.math.BigDecimal;
import java.util.List;

public interface ICardRepository {

    List<Card> findAllByClientId(long clientId);

    Card findOne(long id);

    void persistOnAccount(long accountId, Card card);

    void merge(Card card);

    BigDecimal checkBalance(long id);
}
