package com.eventify.eventify.services.ibge;

import com.eventify.eventify.dto.locations.CityDto;
import com.eventify.eventify.dto.locations.StateDto;
import com.eventify.eventify.port.service.ibge.IBGEDataService;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IBGEDataServiceImpl implements IBGEDataService {

    private final RestTemplate restTemplate;
    private final String IBGE_API_URL_ESTADOS = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
    private final String IBGE_API_URL_MUNICIPIOS = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios";

    public IBGEDataServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StateDto> findStates() {
        StateDto[] statesArray = restTemplate.getForObject(IBGE_API_URL_ESTADOS, StateDto[].class);
        return Arrays.asList(statesArray);
    }

    @Override
    public List<CityDto> findCitiesByState(String st) {
        CityDto[] citiesArray = restTemplate.getForObject(IBGE_API_URL_MUNICIPIOS, CityDto[].class, st);
        return Arrays.asList(citiesArray);
    }

}
