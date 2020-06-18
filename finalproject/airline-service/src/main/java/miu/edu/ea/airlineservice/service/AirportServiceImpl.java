package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Address;
import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.exception.ApiCustomException;
import miu.edu.ea.airlineservice.repository.AirlineRepository;
import miu.edu.ea.airlineservice.repository.AirportRepository;

import miu.edu.ea.airlineservice.service.mapper.AirportMapper;
import miu.edu.ea.airlineservice.service.mapper.FlightMapper;
import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;
import miu.edu.ea.airlineservice.service.response.AirportResponse;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {
    AirportRepository airportRepository;

    public AirportServiceImpl(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }
    @Override
    public List<AirportResponse> findByCode(String code) {
        return airportRepository.findByCode(code).stream().map(AirportMapper::mapToAirportResponse).collect(Collectors.toList());
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return  airportRepository.findAll().stream().map(AirportMapper::mapToAirportResponse).collect(Collectors.toList());


    @Override
    public List<AirportResponse> getAll() {
        return airportRepository.findAll().stream().map(AirportMapper::mapToAirportResponse).collect(Collectors.toList());
    }

    @Override
    public AirportResponse getById(Long id) {
        Airport tempPort = new Airport();
        tempPort.setName("Airport Not Found!");
        Optional<Airport> lasVenturasAirport = airportRepository.findById(id);
        if(lasVenturasAirport.isPresent()){
            tempPort = lasVenturasAirport.get();
        }
        return AirportMapper.mapToAirportResponse(tempPort);
    }

    @Override
    public AirportResponse createOrUpdate(Airport airport) {
        Airport tempPort = airport;
        Optional<Airport> lasVenturasAirport = airportRepository.findById(airport.getId());
        if(lasVenturasAirport.isPresent()) {
            tempPort = lasVenturasAirport.get();
        Airport airport = airportRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airport with id " + id + " is not found"));
        return AirportMapper.mapToAirportResponse(airport);
    }

    @Override
    public AirportResponse create(AirportRequest airportRequest) {
        Airport airport = new Airport();
        airport.setCode(airportRequest.getCode());
        airport.setName(airportRequest.getName());
        airport.setAddress(airportRequest.getAddress());

        return AirportMapper.mapToAirportResponse(airportRepository.save(airport));
    }

    @Override
    public AirportResponse update(AirportRequest airportRequest, Long id) {
        Airport airport = airportRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airport with id " + id + " is not found"));
        airport.setCode(airportRequest.getCode());
        airport.setName(airportRequest.getName());
        airport.setAddress(airportRequest.getAddress());


        //return AirportMapper.mapToAirportResponse(tempPort);

        return AirportMapper.mapToAirportResponse(airportRepository.save(airport));
    }

    @Override
    public void delete(Long id) {
        Airport airport = airportRepository.findById(id).orElseThrow(() -> new ApiCustomException("Airport with id " + id + " is not found"));
        airportRepository.delete(airport);
    }
}
