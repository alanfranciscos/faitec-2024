package com.eventify.eventify.services.opencage;

import com.eventify.eventify.dto.locations.GeocodingDto;
import com.eventify.eventify.port.service.opencage.OpenCageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenCageServiceImpl implements OpenCageService {

    @Value("${opencage.api.key}")
    private String openCageApiKey;

    private final RestTemplate restTemplate;

    private String URL_OPEN_CAGE = "https://api.opencagedata.com/geocode/v1/json?q=%s,%s&key=%s";

    public OpenCageServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public GeocodingDto findGeocodingData(double lat, double lng) {
        String url = String.format(URL_OPEN_CAGE, lat, lng, openCageApiKey);
        return restTemplate.getForObject(url, GeocodingDto.class);
    }
}
