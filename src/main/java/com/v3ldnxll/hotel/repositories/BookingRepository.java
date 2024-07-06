package com.v3ldnxll.hotel.repositories;

import com.v3ldnxll.hotel.types.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    List<Booking> findAll();
    Optional<Booking> findById(int id);
    void save(Booking booking);
    void update(Booking booking);
    void delete(int id);
}