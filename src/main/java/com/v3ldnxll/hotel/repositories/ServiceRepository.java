package com.v3ldnxll.hotel.repositories;

import com.v3ldnxll.hotel.types.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    List<Service> findAll();
    Optional<Service> findById(int id);
    void save(Service service);
    void update(Service service);
    void delete(int id);
}