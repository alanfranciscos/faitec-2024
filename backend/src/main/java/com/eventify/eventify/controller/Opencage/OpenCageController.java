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

    private final OpenCageService openCageService;

    public OpenCageController(OpenCageService openCageService){
        this.openCageService = openCageService;
    }

    @GetMapping("/location")
    public ResponseEntity<GeocodingDto> findLocationData(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
            GeocodingDto geocodingData = openCageService.findGeocodingData(lat, lng);
            if(geocodingData == null){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(geocodingData);
        }
}
    
