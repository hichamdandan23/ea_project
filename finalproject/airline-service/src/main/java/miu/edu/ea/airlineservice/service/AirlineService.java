package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;

import java.util.List;

public interface AirlineService {
    List<Airline> findByCode(String Code);
}
