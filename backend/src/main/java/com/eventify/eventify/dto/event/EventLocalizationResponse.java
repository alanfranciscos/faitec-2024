package com.eventify.eventify.dto.event;

public record EventLocalizationResponse(
        String locationName,
        String zipCode,
        String country,
        String city,
        String neighborhood,
        String street,
        String number,
        String complement,
        Coordinates coordinates
) {
    public record Coordinates(double lat, double lng) {
    }
}
