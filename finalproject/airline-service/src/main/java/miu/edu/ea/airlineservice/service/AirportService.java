package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;

import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;
import miu.edu.ea.airlineservice.service.response.AirportResponse;

import java.util.List;

public interface AirportService {

    List<AirportResponse> findByCode(String Code);
    List<AirportResponse> getAllAirports();
    AirportResponse getById(Long id);
    AirportResponse createOrUpdate(Airport airport);
    Boolean deleteById(Long id);

    List<AirportResponse> getAll();
    AirportResponse create(AirportRequest airportRequest);
    AirportResponse update(AirportRequest airlineRequest, Long id);
    void delete(Long id);

}
