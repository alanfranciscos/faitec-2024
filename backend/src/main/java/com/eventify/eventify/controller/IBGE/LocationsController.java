package com.eventify.eventify.controller.IBGE;

import com.eventify.eventify.dto.locations.CityDto;
import com.eventify.eventify.dto.locations.StateDto;
import com.eventify.eventify.port.service.ibge.IBGEDataService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationsController {

	// Exemplo de requisição dos estados do Brasil
    // http://localhost:8080/locations/states

	// Exemplo de requisição dos municípios de acordo com os estados
    // http://localhost:8080/locations/states/MG/cities

    private IBGEDataService ibgeDataService;

	public LocationsController(IBGEDataService ibgeDataService){
		this.ibgeDataService = ibgeDataService;
	}

	@GetMapping("/states")
	public ResponseEntity<List<StateDto>> findStates() {
		List<StateDto> states = ibgeDataService.findStates();

		if(states.isEmpty()){
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(states);
	}

	@GetMapping("/states/{uf}/cities")
	public ResponseEntity<List<CityDto>> getCities(@PathVariable String uf) {
		List<CityDto> cities = ibgeDataService.findCitiesByState(uf);

		if(cities.isEmpty()){
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cities);
	}
}
