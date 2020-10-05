package ru.sberstart.repository.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.bootstrap.ServiceLocator;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.CardRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class CardRepositoryImpl implements CardRepository {
    private final Bootstrap bootstrap;

    @Override
    public List<Card> findAllByClientId(long clientId) {
        return null;
    }

    @Override
    public Card findOne(long id) {
        return null;
    }

    @Override
    public void persistOnAccount(long accountId, Card card) {

    }

    @Override
    public void merge(Card card) {

    }

    @Override
    public BigDecimal checkBalance(long id) {
        return null;
    }
}
