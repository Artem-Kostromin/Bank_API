package ru.sberstart.repository;

import ru.sberstart.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    Account findOne(long id) throws SQLException;

    List<Account> findAll() throws SQLException;

    Account persist(Account account) throws SQLException;
}
