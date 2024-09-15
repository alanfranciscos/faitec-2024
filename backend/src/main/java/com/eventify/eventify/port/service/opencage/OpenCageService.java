package com.eventify.eventify.port.service.opencage;

import com.eventify.eventify.dto.locations.GeocodingDto;

public interface OpenCageService {
    GeocodingDto findGeocodingData(double lat, double lng);
}
