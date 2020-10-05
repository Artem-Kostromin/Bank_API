package ru.sberstart.repository.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final Bootstrap bootstrap;

    public Account findOne(long id) throws SQLException {
        Connection con = bootstrap.getConnection();
        PreparedStatement prStatement = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
        prStatement.setLong(0, id);
        ResultSet rs = prStatement.executeQuery();
        return fetch(rs);

    }

    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Connection con = bootstrap.getConnection();
        PreparedStatement prStatement = con.prepareStatement("SELECT * FROM accounts");
        ResultSet rs = prStatement.executeQuery();
        while(rs.next()) {
            accounts.add(fetch(rs));
        }
        return accounts;
    }

    public Account persist(Account account) throws SQLException {
        Connection con = bootstrap.getConnection();
        PreparedStatement prStatement = con.prepareStatement("INSERT INTO accounts values (default)",
                Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = prStatement.getGeneratedKeys();
        account.setId(rs.getLong("id"));
        return account;
    }



    private Account fetch(ResultSet row) throws SQLException {
        if(row == null) return null;
        Account account = new Account();
        account.setId(Long.parseLong(row.getString("id")));
        return account;
    }
}
