package com.eventify.eventify.dto.event;

public record EventLocalizationResponse(
        String LocationName,
        String zipCode,
        String country,
        String city,
        String Neighborhood,
        String street,
        String Number,
        String complement,
        Coordinates coordinates
) {
    public record Coordinates(double latitude, double longitude) {
    }
}
