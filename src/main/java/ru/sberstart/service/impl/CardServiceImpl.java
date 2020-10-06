package ru.sberstart.service.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.CardRepository;
import ru.sberstart.service.CardService;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    @Override
    public List<Card> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Card> findAllByAccount(long accountId) {
        return repository.findAllByAccount(accountId);
    }

    @Override
    public Card findOne(long id) {
        return repository.findOne(id);
    }

    @Override
    public Card persist(long accountId, Card card) {
        return repository.persist(accountId, card);
    }

    @Override
    public boolean removeOne(long id) {
        return repository.removeOne(id);
    }

    @Override
    public BigDecimal checkBalance(long id) {
        return repository.checkBalance(id);
    }
}
