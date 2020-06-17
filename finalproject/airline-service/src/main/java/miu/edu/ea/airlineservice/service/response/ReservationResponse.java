package miu.edu.ea.airlineservice.service.response;

import miu.edu.ea.airlineservice.domain.ReservationStatus;

import java.util.List;

public class ReservationResponse {
    private Long id;
    private ReservationStatus reservationStatus;
    private String passengerId;
    private String createdById;
    private String reservationCode;
    private List<FlightResponse> flights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public List<FlightResponse> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightResponse> flights) {
        this.flights = flights;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }
}
