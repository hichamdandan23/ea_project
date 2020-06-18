package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.service.response.AirportResponse;

import java.util.List;

public interface AirportService {
    List<AirportResponse> findByCode(String Code);
    List<AirportResponse> getAllAirports();
    AirportResponse getById(Long id);
    AirportResponse createOrUpdate(Airport airport);
    Boolean deleteById(Long id);
}
