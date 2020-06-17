package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.AirlineRepository;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineServiceImpl implements AirlineService {
    AirlineRepository airlineRepository;

    public AirlineServiceImpl(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }
    @Override
    public List<Airline> findByCode(String code) {
        return airlineRepository.findByCode(code);
    }
}
