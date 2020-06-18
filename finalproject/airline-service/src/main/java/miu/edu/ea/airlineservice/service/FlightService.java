package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.request.FlightRequest;
import miu.edu.ea.airlineservice.service.response.AirportResponse;
import miu.edu.ea.airlineservice.service.response.FlightResponse;

import java.util.List;

public interface FlightService {
    List<FlightResponse> getAll();
    FlightResponse getById(Long id);
    FlightResponse create(FlightRequest flightRequest);
    FlightResponse update(FlightRequest flightRequest, Long id);

    List<FlightResponse> getByArrivalAndDeparture(String departureAirportCode, String arrivalAirportCode);

    void delete(Long id);
}
