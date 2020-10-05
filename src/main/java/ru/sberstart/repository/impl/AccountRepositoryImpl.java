package ru.sberstart.repository.impl;

import lombok.AllArgsConstructor;
import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.entity.Account;
import ru.sberstart.repository.AccountRepository;
import ru.sberstart.util.db.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final Connection connection;

    public Account findOne(long id) throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?");
        prStatement.setLong(1, id);
        ResultSet rs = prStatement.executeQuery();
        return fetch(rs);

    }

    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        PreparedStatement prStatement = connection.prepareStatement("SELECT * FROM accounts");
        ResultSet rs = prStatement.executeQuery();
        while(rs.next()) {
            accounts.add(fetch(rs));
        }
        return accounts;
    }

    public Account persist(Account account) throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement("INSERT INTO accounts values (default)",
                Statement.RETURN_GENERATED_KEYS);
        prStatement.executeUpdate();
        ResultSet rs = prStatement.getGeneratedKeys();
        if (rs.next()) {
            long generatedKey = rs.getLong(1);
            account.setId(generatedKey);
        }
        return account;
    }



    private Account fetch(ResultSet row) throws SQLException {
        if(row == null) return null;
        Account account = new Account();
        account.setId(Long.parseLong(row.getString("id")));
        return account;
    }
}
