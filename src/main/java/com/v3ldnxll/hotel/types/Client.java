package com.v3ldnxll.hotel.types;

public record Client(
    int id,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    String address
) {}