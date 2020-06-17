package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FlightService {
    List<Flight> findDepartureByCodeOrArrivalByCode(String DCode, String ACode);
    Page<Flight> findByAirportCode(String dCode, String aCode, Pageable pageable);
    List<Flight> getAllFlights();
    Flight getById(Long id);
    Flight createOrUpdate(Flight flight);
    Boolean deleteById(Long id);
}
