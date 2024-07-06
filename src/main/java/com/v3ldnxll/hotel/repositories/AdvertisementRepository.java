package com.v3ldnxll.hotel.repositories;

import com.v3ldnxll.hotel.types.Advertisement;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository {
    List<Advertisement> findAll();
    Optional<Advertisement> findById(int id);
    void save(Advertisement advertisement);
    void update(Advertisement advertisement);
    void delete(int id);
}