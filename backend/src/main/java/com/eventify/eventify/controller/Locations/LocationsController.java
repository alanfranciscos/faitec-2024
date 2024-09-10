package com.eventify.eventify.controller.Locations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LocationsController {

    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

    

}
