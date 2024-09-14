package com.eventify.eventify.port.service.opencage;

import com.eventify.eventify.dto.locations.GeocodingDto;

public interface OpenCageService {
    GeocodingDto getGeocodingData(double lat, double lng);
}
