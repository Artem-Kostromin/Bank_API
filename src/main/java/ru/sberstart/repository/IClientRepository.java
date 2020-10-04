package ru.sberstart.repository;

import ru.sberstart.entity.Client;

import java.util.List;

public interface IClientRepository {

     List<Client> findAll();

     Client findOne(long id);

     void persistOne(Client client);

     void persistAll(List<Client> clients);

     void merge(Client client);

     void removeOne(long id);

     void removeAll();
}
