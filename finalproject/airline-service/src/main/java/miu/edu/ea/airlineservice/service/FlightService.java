package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;

import miu.edu.ea.airlineservice.service.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FlightService {
    List<FlightResponse> findDepartureByCodeOrArrivalByCode(String DCode, String ACode);
    Page<FlightResponse> findByAirportCode(String dCode, String aCode, Pageable pageable);
    List<FlightResponse> getAllFlights();
    FlightResponse getById(Long id);
    FlightResponse createOrUpdate(Flight flight);
    Boolean deleteById(Long id);
}
