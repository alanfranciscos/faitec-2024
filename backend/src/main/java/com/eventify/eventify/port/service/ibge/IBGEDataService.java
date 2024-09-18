package com.eventify.eventify.port.service.ibge;

import com.eventify.eventify.dto.locations.CityDto;
import com.eventify.eventify.dto.locations.StateDto;
import java.util.List;

public interface IBGEDataService {
    List<StateDto> findStates();
    List<CityDto> findCitiesByState(String st);
}
