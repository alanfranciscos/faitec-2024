package com.eventify.eventify.dto.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StateDto(Long id, String sigla, String nome) { }
