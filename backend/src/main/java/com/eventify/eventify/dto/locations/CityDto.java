package com.eventify.eventify.dto.locations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CityDto(Long id, String nome) { }
