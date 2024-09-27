package com.eventify.eventify.services.ibge;

import com.eventify.eventify.dto.locations.CityDto;
import com.eventify.eventify.dto.locations.StateDto;
import com.eventify.eventify.port.service.ibge.IBGEDataService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IBGEDataServiceImpl implements IBGEDataService {

    private final RestTemplate restTemplate;

    @Value("${base.url.ibge.states}")
    private String IBGE_API_URL_ESTADOS;
    
    @Value("${base.url.ibge.cities}")
    private String IBGE_API_URL_MUNICIPIOS;

    public IBGEDataServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StateDto> findStates() {
        String SPECIFIC_STATES_URL = "api/v1/localidades/estados";
        StateDto[] statesArray = restTemplate.getForObject(IBGE_API_URL_ESTADOS + SPECIFIC_STATES_URL, StateDto[].class);
        return Arrays.asList(statesArray);
    }

    @Override
    public List<CityDto> findCitiesByState(String st) {
        String SPECIFIC_CITIES_URL = "api/v1/localidades/estados/{UF}/municipios";
        CityDto[] citiesArray = restTemplate.getForObject(IBGE_API_URL_MUNICIPIOS + SPECIFIC_CITIES_URL, CityDto[].class, st);
        return Arrays.asList(citiesArray);
    }

}
