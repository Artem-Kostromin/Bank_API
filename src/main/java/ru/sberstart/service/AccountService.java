package ru.sberstart.service;

import ru.sberstart.entity.Account;

import java.util.List;

public interface AccountService {
    Account findOne(long id);

    List<Account> findAll();

    Account persist(Account account);

    boolean removeOne(long id);
}
