package ru.sberstart.repository.impl;

import ru.sberstart.bootstrap.Bootstrap;
import ru.sberstart.entity.Client;
import ru.sberstart.repository.IClientRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ClientRepositoryImpl implements IClientRepository {
    private final Bootstrap bootstrap;

    public ClientRepositoryImpl(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }


    @Override
    public List<Client> findAll() {
        Connection connection = bootstrap.getConnection();
        try {
            connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findOne(long id) {
        return null;
    }

    @Override
    public void persistOne(Client client) {
        Connection connection = bootstrap.getConnection();
        try {
            Statement st = connection.createStatement();
            st.executeQuery("INSERT INTO  ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void persistAll(List<Client> clients) {

    }

    @Override
    public void merge(Client client) {

    }

    @Override
    public void removeOne(long id) {

    }

    @Override
    public void removeAll() {

    }
}
