package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;

public class AirlineMapper {
    public static AirlineResponse mapToAirlineResponse(Airline airline){
        AirlineResponse airlineResponse = new AirlineResponse();
        airlineResponse.setId(airline.getId());
        airlineResponse.setCode(airline.getCode());
        airlineResponse.setName(airline.getName());
        return airlineResponse;
    }
}
