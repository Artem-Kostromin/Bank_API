package ru.sberstart.repository;

import ru.sberstart.entity.Account;

import java.util.List;

public interface AccountRepository {
    Account findOne(long id);

    List<Account> findAll();

    Account persist(Account account);

    boolean removeOne(long id);
}
