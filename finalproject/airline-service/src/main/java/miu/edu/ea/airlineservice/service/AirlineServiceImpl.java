package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.AirlineRepository;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }

    @Override
    public Airline getById(Long id) {
        Airline tempLine = new Airline();
        tempLine.setName("Airline Not Found!");
        Optional<Airline> airForceOne = airlineRepository.findById(id);
        if(airForceOne.isPresent()){
            tempLine = airForceOne.get();
        }
        return tempLine;
    }

    @Override
    public Airline createOrUpdate(Airline airline) {
        Airline tempAir = airline;
        Optional<Airline> airForceOne = airlineRepository.findById(airline.getId());
        if(airForceOne.isPresent()) {
            tempAir = airForceOne.get();

            tempAir.setCode(airline.getCode());
            tempAir.setName(airline.getName());
            tempAir.setHistory(airline.getHistory());
        }

        airlineRepository.save(tempAir);

        return airline;
    }

    @Override
    public Boolean deleteById(Long id) {
        boolean success = false;
        Optional<Airline> airForceOne = airlineRepository.findById(id);
        if(airForceOne.isPresent()){
            airlineRepository.deleteById(id);
            success = true;
        }
        return success;
    }
}
