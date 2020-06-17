package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Flight;

import java.awt.print.Pageable;
import java.util.List;

public interface FlightService {
    List<Flight> findDepartureByCodeOrArrivalByCode(String DCode, String ACode);
    List<Flight> findByAirportCode(String dCode, String aCode, Pageable pageable);
}
