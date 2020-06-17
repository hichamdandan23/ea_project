package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> findDepartureByCodeOrArrivalByCode(String DCode, String ACode);
}
