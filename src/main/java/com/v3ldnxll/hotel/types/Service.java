package com.v3ldnxll.hotel.types;

public record Service(
    int id,
    String name,
    String description,
    double price
) {}