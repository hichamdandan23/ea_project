package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.response.FlightResponse;

public class FlightMapper {
    public static FlightResponse mapToFlightResponse(Flight flight){
        FlightResponse flightResponse = new FlightResponse();
        flightResponse.setId(flight.getId());
        flightResponse.setCapacity(flight.getCapacity());
        flightResponse.setNumber(flight.getNumber());
        flightResponse.setArrivalTime(flight.getArrivalTime());
        flightResponse.setDepartureTime(flight.getDepartureTime());
        flightResponse.setAirline(AirlineMapper.mapToAirlineResponse(flight.getAirline()));
        flightResponse.setDeparture(AirportMapper.mapToAirportResponse(flight.getDeparture()));
        flightResponse.setArrival(AirportMapper.mapToAirportResponse(flight.getArrival()));
        return flightResponse;
    }

}
