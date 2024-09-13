package com.eventify.eventify.port.service.viacep;

import com.eventify.eventify.dto.locations.ViaCepDto;

public interface ViaCepService {
    ViaCepDto getAdressByPostalCode(String cep);
}
