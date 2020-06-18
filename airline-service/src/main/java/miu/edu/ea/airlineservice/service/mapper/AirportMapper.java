package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.service.response.AirportResponse;

public class AirportMapper {
    public static AirportResponse mapToAirportResponse(Airport airport){
        AirportResponse airportResponse = new AirportResponse();
        airportResponse.setId(airport.getId());
        airportResponse.setName(airport.getName());
        airportResponse.setCode(airport.getCode());
        airportResponse.setAddress(airport.getAddress());
        return airportResponse;
    }
}
