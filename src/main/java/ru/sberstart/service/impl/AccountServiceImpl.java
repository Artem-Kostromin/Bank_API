package ru.sberstart.service.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.service.AccountService;

import java.util.List;

@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;

    @Override
    public Account findOne(long id) {
        return repository.findOne(id);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Account persist(Account account) {
        return repository.persist(account);
    }

    @Override
    public boolean removeOne(long id) {
        return repository.removeOne(id);
    }
}
