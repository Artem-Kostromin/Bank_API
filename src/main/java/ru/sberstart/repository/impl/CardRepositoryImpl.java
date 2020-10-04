package ru.sberstart.repository.impl;

import ru.sberstart.bootstrap.ServiceLocator;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.ICardRepository;

import java.math.BigDecimal;
import java.util.List;

public class CardRepositoryImpl implements ICardRepository {
    private final ServiceLocator bootstrap;

    public CardRepositoryImpl(ServiceLocator bootstrap) {
        this.bootstrap = bootstrap;
    }

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
