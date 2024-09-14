package com.eventify.eventify.controller.IBGE;

import com.eventify.eventify.dto.locations.CityDto;
import com.eventify.eventify.dto.locations.StateDto;
import com.eventify.eventify.port.service.ibge.IBGEDataService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LocationsController {

	// Exemplo de requisição dos estados do Brasil
    // http://localhost:8080/states

	// Exemplo de requisição dos municípios de acordo com os estados
    // http://localhost:8080/states/MG/cities

    private IBGEDataService ibgeDataService;

	public LocationsController(IBGEDataService ibgeDataService){
		this.ibgeDataService = ibgeDataService;
	}

	@GetMapping("/states")
	public List<StateDto> getStates() {
		return ibgeDataService.getStates();
	}

	@GetMapping("/states/{uf}/cities")
	public List<CityDto> getCities(@PathVariable String uf) {
		return ibgeDataService.getCitiesByState(uf);
	}
	

}
