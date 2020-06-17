package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.awt.print.Pageable;
import java.util.ArrayList;
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

    @Override
    public List<Flight> findByAirportCode(String dCode, String aCode, Pageable pageable) {
        List<Flight> flights = flightRepository.findAll(
                new Specification<Flight>() {
                    @Override
                    public Predicate toPredicate(Root<Flight> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();

                        if(dCode != null && !dCode.equals("")) {
                            Join<Flight, Airport> join = root.join("departure", JoinType.INNER);
                            predicates.add(criteriaBuilder.equal(join.get("code"), dCode));
                        }

                        if(aCode != null && aCode.equals("")) {
                            Join<Flight, Airport> join = root.join("arrival", JoinType.INNER);
                            predicates.add(criteriaBuilder.equal(join.get("code"), aCode));
                        }

                        return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                                .getRestriction();
                    }
                });
        return flights;
    }
}
