package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.exception.ApiCustomException;
import miu.edu.ea.airlineservice.repository.AirlineRepository;
import miu.edu.ea.airlineservice.repository.AirportRepository;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.service.mapper.AirportMapper;
import miu.edu.ea.airlineservice.service.mapper.FlightMapper;
import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.request.FlightRequest;
import miu.edu.ea.airlineservice.service.response.AirportResponse;
import miu.edu.ea.airlineservice.service.response.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
    FlightRepository flightRepository;
    AirportRepository airportRepository;
    AirlineRepository airlineRepository;

    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository, AirlineRepository airlineRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.airlineRepository = airlineRepository;
    }

    @Override
    public List<FlightResponse> getAll() {
        return flightRepository.findAll().stream().map(FlightMapper::mapToFlightResponse).collect(Collectors.toList());
    }

    @Override
    public FlightResponse getById(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new ApiCustomException("Flight with id " + id + " is not found"));
        return FlightMapper.mapToFlightResponse(flight);
    }

    @Override
    public List<FlightResponse> getByArrivalAndDeparture(String departureAirportCode, String arrivalAirportCode) {
        return flightRepository.getByIdArrivalAndDeparture(departureAirportCode, arrivalAirportCode).stream().map(FlightMapper::mapToFlightResponse).collect(Collectors.toList());
    }

    @Override
    public FlightResponse create(FlightRequest flightRequest) {
        Flight flight = new Flight();
        flight.setAirline(airlineRepository.findById(flightRequest.getAirlineId())
                .orElseThrow(()-> new ApiCustomException("Airline with id "+flightRequest.getAirlineId()+" is not found")));
        flight.setDeparture(airportRepository.findById(flightRequest.getDepartureAirportId())
                .orElseThrow(()-> new ApiCustomException("Airport with id "+flightRequest.getDepartureAirportId()+" is not found")));
        flight.setArrival(airportRepository.findById(flightRequest.getArrivalAirportId())
                .orElseThrow(()-> new ApiCustomException("Airport with id "+flightRequest.getArrivalAirportId()+" is not found")));
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setCapacity(flightRequest.getCapacity());
        flight.setNumber(flightRequest.getNumber());
        return FlightMapper.mapToFlightResponse(flightRepository.save(flight));
    }

    @Override
    public FlightResponse update(FlightRequest flightRequest, Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new ApiCustomException("Flight with id " + id + " is not found"));

        flight.setArrival(airportRepository.findById(flightRequest.getArrivalAirportId())
                .orElseThrow(()-> new ApiCustomException("Airport with id "+flightRequest.getArrivalAirportId()+" is not found")));
        flight.setAirline(airlineRepository.findById(flightRequest.getAirlineId())
                .orElseThrow(()-> new ApiCustomException("Airline with id "+flightRequest.getAirlineId()+" is not found")));
        flight.setDeparture(airportRepository.findById(flightRequest.getDepartureAirportId())
                .orElseThrow(()-> new ApiCustomException("Airport with id "+flightRequest.getDepartureAirportId()+" is not found")));
        flight.setArrivalTime(flightRequest.getArrivalTime());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setCapacity(flightRequest.getCapacity());
        flight.setNumber(flightRequest.getNumber());
        return FlightMapper.mapToFlightResponse(flightRepository.save(flight));

    }

    @Override
    public void delete(Long id) {
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new ApiCustomException("Flight with id " + id + " is not found"));
        flightRepository.delete(flight);
    }
}
