package com.eventify.eventify.dto.locations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record CepDto(String logradouro, String bairro, String localidade, String uf, String ddd) {
    
}