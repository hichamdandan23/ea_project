package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;

import java.util.List;

public interface AirlineService {
    List<Airline> findByCode(String Code);
    List<Airline> getAllAirlines();
    Airline getById(Long id);
    Airline createOrUpdate(Airline airline);
    Boolean deleteById(Long id);
}
