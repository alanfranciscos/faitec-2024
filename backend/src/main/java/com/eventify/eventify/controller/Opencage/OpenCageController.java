package com.eventify.eventify.controller.Opencage;

import com.eventify.eventify.dto.locations.GeocodingDto;
import com.eventify.eventify.port.service.opencage.OpenCageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/geocoding")
public class OpenCageController {

    // Exemplo de requisição
    // http://localhost:8080/geocoding/location?lat=-22.2536521&lng=-45.6950728
    // https://api.opencagedata.com/geocode/v1/json?q=-22.2536521%2C-45.6950728&key=87b230d9b8b24c5b990c82b02d1acdf7

    private final OpenCageService openCageService;

    public OpenCageController(OpenCageService openCageService){
        this.openCageService = openCageService;
    }

    @GetMapping("/location")
    public ResponseEntity<GeocodingDto.Components> findLocationData(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        GeocodingDto geocodingData = openCageService.findGeocodingData(lat, lng);
        System.out.println();
        if(geocodingData == null){
            return ResponseEntity.noContent().build();
        }
        GeocodingDto.Result result = geocodingData.getResults().get(0);
        GeocodingDto.Components components = result.getComponents();
        return ResponseEntity.ok(components);
        }
}
    
