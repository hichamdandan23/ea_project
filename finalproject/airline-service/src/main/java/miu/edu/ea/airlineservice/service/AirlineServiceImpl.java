package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.exception.ApiCustomException;
import miu.edu.ea.airlineservice.repository.AirlineRepository;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.service.mapper.AirlineMapper;
import miu.edu.ea.airlineservice.service.mapper.AirportMapper;
import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {
    AirlineRepository airlineRepository;

    public AirlineServiceImpl(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    @Override
    public List<AirlineResponse> findByAirportCode(String code) {
        return airlineRepository.findByAirportCode(code).stream().map(AirlineMapper::mapToAirlineResponse).collect(Collectors.toList());
    }

    @Override
    public List<AirlineResponse> getAllAirlines() {
        return airlineRepository.findAll().stream().map(AirlineMapper::mapToAirlineResponse).collect(Collectors.toList());
    }

    @Override
    public AirlineResponse getById(Long id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airline with id " + id + " is not found"));
        return AirlineMapper.mapToAirlineResponse(airline);
    }

    @Override
    public AirlineResponse createAirline(AirlineRequest airlineRequest) {
        Airline airline = new Airline();
        airline.setCode(airlineRequest.getCode());
        airline.setName(airlineRequest.getName());
        airline.setHistory(airlineRequest.getHistory());

        return AirlineMapper.mapToAirlineResponse(airlineRepository.save(airline));
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest airlineRequest, Long id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airline with id " + id + " is not found"));
        airline.setCode(airlineRequest.getCode());
        airline.setName(airlineRequest.getName());
        airline.setHistory(airlineRequest.getHistory());

        return AirlineMapper.mapToAirlineResponse(airlineRepository.save(airline));
    }

    @Override
    public void delete(Long id) {
        Airline airline = airlineRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airline with id " + id + " is not found"));
        airlineRepository.delete(airline);
    }
}
