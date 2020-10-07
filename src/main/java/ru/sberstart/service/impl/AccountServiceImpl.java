package ru.sberstart.service.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.service.AccountService;

import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Override
    public Account findOne(long id) {
        try {
            return repository.findOne(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        try {
            return repository.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Account persist(Account account) {
        try {
            return repository.persist(account);
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
}
