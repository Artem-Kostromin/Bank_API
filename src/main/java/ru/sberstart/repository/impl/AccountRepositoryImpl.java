package ru.sberstart.repository.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final Connection connection;

    @SneakyThrows
    public Account findOne(long id) {
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?");
        prStatement.setLong(1, id);
        ResultSet rs = prStatement.executeQuery();
        rs.next();
        Account account = fetch(rs);
        rs.close();
        prStatement.close();
        return account;

    }

    @SneakyThrows
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM accounts");
        ResultSet rs = prStatement.executeQuery();
        while(rs.next()) {
            accounts.add(fetch(rs));
        }
        prStatement.close();
        return accounts;
    }

    @SneakyThrows
    public Account persist(Account account) {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO accounts values (default)",
                Statement.RETURN_GENERATED_KEYS);
        prStatement.executeUpdate();
        ResultSet rs = prStatement.getGeneratedKeys();
        if (rs.next()) {
            long id = rs.getLong(1);
            account.setId(id);
        }
        prStatement.close();
        return account;
    }

    @SneakyThrows
    public boolean removeOne(long id) {
        PreparedStatement prStatement = connection.prepareStatement("DELETE FROM accounts WHERE id = ?");
        prStatement.setLong(1, id);
        int i = prStatement.executeUpdate();
        prStatement.close();
        return i > 0;
    }



    private Account fetch(ResultSet row) throws SQLException {
        if(row == null) return null;
        Account account = new Account();
        account.setId(row.getLong("id"));
        return account;
    }
}
