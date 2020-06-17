package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getById(Long id) {
        Flight tempLight = new Flight();
        tempLight.setNumber("Flight Not Found!");
        Optional<Flight> flightOfTheBumblebee = flightRepository.findById(id);
        if(flightOfTheBumblebee.isPresent()){
            tempLight = flightOfTheBumblebee.get();
        }
        return tempLight;
    }

    @Override
    public Flight createOrUpdate(Flight flight) {
        Flight tempLight = flight;
        Optional<Flight> flightOfTheBumblebee = flightRepository.findById(flight.getId());
        if(flightOfTheBumblebee.isPresent()) {
            tempLight = flightOfTheBumblebee.get();

            tempLight.setNumber(flight.getNumber());
            tempLight.setAirline(flight.getAirline());
            tempLight.setArrival(flight.getArrival());
            tempLight.setCapacity(flight.getCapacity());
            tempLight.setDeparture(flight.getDeparture());
            tempLight.setDepartureTime(flight.getDepartureTime());
            tempLight.setReservations(flight.getReservations());
        }

        flightRepository.save(tempLight);

        return tempLight;
    }

    @Override
    public Boolean deleteById(Long id) {
        boolean success = false;
        Optional<Flight> flightOfTheBumblebee = flightRepository.findById(id);
        if(flightOfTheBumblebee.isPresent()){
            flightRepository.deleteById(id);
            success = true;
        }
        return success;
    }
}
