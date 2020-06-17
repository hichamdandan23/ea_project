package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;

import java.util.List;

public interface AirlineService {
    List<AirlineResponse> findByAirportCode(String Code);
    List<AirlineResponse> getAllAirlines();
    AirlineResponse getById(Long id);
    AirlineResponse createAirline(AirlineRequest airlineRequest);
    AirlineResponse updateAirline(AirlineRequest airlineRequest, Long id);
    void delete(Long id);
}
