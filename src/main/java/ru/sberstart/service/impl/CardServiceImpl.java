package ru.sberstart.service.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.entity.Card;
import ru.sberstart.repository.CardRepository;
import ru.sberstart.service.CardService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository repository;

    @Override
    public List<Card> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Card> findAllByAccount(long accountId) {
        try {
            return repository.findAllByAccount(accountId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Card findOne(long id) {
        try {
            return repository.findOne(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Card persist(long accountId, Card card) {
        try {
            return repository.persist(accountId, card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeOne(long id) {
        try {
            return repository.removeOne(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Card update(long id, Card card) {
        try {
            return repository.update(id, card);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public BigDecimal checkBalance(long id) {
        try {
            return repository.checkBalance(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
