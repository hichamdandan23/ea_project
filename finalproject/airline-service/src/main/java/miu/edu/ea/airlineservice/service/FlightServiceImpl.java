package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {
    FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    @Override
    public List<Flight> findDepartureByCodeOrArrivalByCode(String DCode, String ACode) {
        return flightRepository.findByDepartureOrArrival(DCode, ACode);
    }
}
