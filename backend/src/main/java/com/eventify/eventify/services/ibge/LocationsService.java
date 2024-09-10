package com.eventify.eventify.services.ibge;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eventify.eventify.dto.locations.StateDto;

@Service
public class LocationsService {

    public List<StateDto> readState(RestTemplate restTemplate){
        String urlState = "https://servicodados.ibge.gov.br/api/v1/localidades/estados?orderBy=nome";

		//Coletando os estados do Brasil
		List<StateDto> states = restTemplate.exchange(
				urlState,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<StateDto>>() {}
		).getBody();

        return states;
    }

}
