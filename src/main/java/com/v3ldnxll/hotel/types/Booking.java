package com.v3ldnxll.hotel.types;

public record Booking(
    int id,
    int clientId,
    int serviceId,
    String startDate,
    String endDate,
    double totalPrice
) {}