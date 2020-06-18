package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.service.mapper.FlightMapper;
import miu.edu.ea.airlineservice.service.response.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

   /* @Override
    public Page<Flight> findByAirportCode(String dCode, String aCode, Pageable pageable) {
         return flightRepository.findAll(new Specification<Flight>() {
            @Override
            public Predicate toPredicate(Root<Flight> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(dCode != null && !dCode.equals("")) {
                    Join<Flight, Airport> join = root.join("departure", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("code"), dCode));
                }
                if(aCode != null && !aCode.equals("")) {
                    Join<Flight, Airport> join = root.join("arrival", JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(join.get("code"), aCode));
                }
                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                        .getRestriction();
            }
        }, pageable);
    }*/

    @Override
    public List<FlightResponse> findDepartureByCodeOrArrivalByCode(String DCode, String ACode) {
        return flightRepository.findAll().stream().map(FlightMapper::mapToFlightResponse).collect(Collectors.toList());
    }

    @Override
    public Page<FlightResponse> findByAirportCode(String dCode, String aCode, Pageable pageable) {
        return null;
    }


    @Override
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(FlightMapper::mapToFlightResponse).collect(Collectors.toList());
    }

    @Override
    public FlightResponse getById(Long id) {
        Flight tempLight = new Flight();
        tempLight.setNumber("Flight Not Found!");
        Optional<Flight> flightOfTheBumblebee = flightRepository.findById(id);
        if(flightOfTheBumblebee.isPresent()){
            tempLight = flightOfTheBumblebee.get();
        }
        return FlightMapper.mapToFlightResponse(tempLight);
    }

    @Override
    public FlightResponse createOrUpdate(Flight flight) {
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

        return FlightMapper.mapToFlightResponse(tempLight);
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
