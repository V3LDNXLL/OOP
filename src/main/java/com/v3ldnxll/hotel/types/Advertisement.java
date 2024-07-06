package com.v3ldnxll.hotel.types;

public record Advertisement(
    int id,
    String title,
    String content,
    String startDate,
    String endDate
) {}