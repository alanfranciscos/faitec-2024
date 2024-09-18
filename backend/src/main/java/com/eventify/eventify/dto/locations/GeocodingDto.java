package com.eventify.eventify.dto.locations;

import java.util.List;

public record GeocodingDto(List<Result> results) {

    public record Result(String formatted) {
    }
}
