package com.eventify.eventify.dto.locations;

import lombok.Data;

@Data
public class ViaCepDto {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String ddd;
}

