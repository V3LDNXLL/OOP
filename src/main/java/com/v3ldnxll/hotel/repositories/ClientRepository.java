package com.v3ldnxll.hotel.repositories;

import com.v3ldnxll.hotel.types.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll();

    Optional<Client> findById(int id);

    void save(Client client);

    void update(Client client);

    void delete(int id);
}