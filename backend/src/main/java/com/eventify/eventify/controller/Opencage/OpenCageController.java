package com.eventify.eventify.controller.Opencage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventify.eventify.dto.locations.GeocodingDto;
import com.eventify.eventify.port.service.opencage.OpenCageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/geocoding")
public class OpenCageController {

    // Exemplo de requisição
    // http://localhost:8080/geocoding/location?lat=-22.2536521&lng=-45.6950728

    private final OpenCageService openCageService;

    public OpenCageController(OpenCageService openCageService){
        this.openCageService = openCageService;
    }

    @GetMapping("/location")
    public ResponseEntity<?> getLocarionData(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        try{
            GeocodingDto response = openCageService.getGeocodingData(lat, lng);
            if(response != null && !response.getResults().isEmpty()){
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    
