package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;

import java.util.List;

public interface AirportService {
    List<Airport> findByCode(String Code);
}
