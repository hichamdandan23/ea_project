package miu.edu.ea.airlineservice.service.request;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class FlightRequest {
    @NotEmpty
    private String number;
    @NotEmpty
    private Integer capacity;
    @NotEmpty
    private LocalDateTime departureTime;
    @NotEmpty
    private LocalDateTime arrivalTime;
    @NotEmpty
    private Long departureAirportId;
    @NotEmpty
    private Long arrivalAirportId;
    @NotEmpty
    private Long airlineId;

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

    public Long getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(Long departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public Long getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(Long arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
}
