package com.eventify.eventify.services.viacep;

import com.eventify.eventify.dto.locations.ViaCepDto;
import com.eventify.eventify.port.service.viacep.ViaCepService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepServiceImpl implements ViaCepService {

    private final RestTemplate restTemplate;
    private final String POSTAL_CODE_URL = "https://viacep.com.br/ws/";

    public ViaCepServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ViaCepDto findAdressByPostalCode(String cep) {
        String url = POSTAL_CODE_URL + cep + "/json/";
        return restTemplate.getForObject(url, ViaCepDto.class);
    }
}
