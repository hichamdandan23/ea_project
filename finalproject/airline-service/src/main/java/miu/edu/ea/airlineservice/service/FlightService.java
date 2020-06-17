package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> findDepartureByCodeOrArrivalByCode(String DCode, String ACode);
    List<Flight> getAllFlights();
    Flight getById(Long id);
    Flight createOrUpdate(Flight flight);
    Boolean deleteById(Long id);
}
