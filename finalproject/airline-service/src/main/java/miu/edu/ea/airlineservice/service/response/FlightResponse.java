package miu.edu.ea.airlineservice.service.response;

import java.time.LocalDateTime;

public class FlightResponse {
    private Long id;
    private String number;
    private Integer capacity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private AirportResponse departure;
    private AirportResponse arrival;
    private AirlineResponse airline;

    public FlightResponse(){}

    public FlightResponse(Long id, String number, Integer capacity, LocalDateTime departureTime, LocalDateTime arrivalTime, AirportResponse departure, AirportResponse arrival, AirlineResponse airline) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departure = departure;
        this.arrival = arrival;
        this.airline = airline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public AirportResponse getDeparture() {
        return departure;
    }

    public void setDeparture(AirportResponse departure) {
        this.departure = departure;
    }

    public AirportResponse getArrival() {
        return arrival;
    }

    public void setArrival(AirportResponse arrival) {
        this.arrival = arrival;
    }

    public AirlineResponse getAirline() {
        return airline;
    }

    public void setAirline(AirlineResponse airline) {
        this.airline = airline;
    }
}
