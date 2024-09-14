package com.eventify.eventify.services.opencage;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import com.eventify.eventify.dto.locations.GeocodingDto;
import com.eventify.eventify.port.service.opencage.OpenCageService;

@Service
public class OpenCageServiceImpl implements OpenCageService {

    @Value("${opencage.api.key}")
    private String openCageApiKey;

    private final RestTemplate restTemplate;

    private String urlOpenCage = "https://api.opencagedata.com/geocode/v1/json?q=%s,%s&key=%s";

    public OpenCageServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public GeocodingDto getGeocodingData(double lat, double lng) {
        String url = String.format(urlOpenCage, lat, lng, openCageApiKey);
        try{
            ResponseEntity<GeocodingDto> response = restTemplate.getForEntity(url, GeocodingDto.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Client Error: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Server Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
