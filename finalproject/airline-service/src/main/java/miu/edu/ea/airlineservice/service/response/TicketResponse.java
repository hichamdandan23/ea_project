package miu.edu.ea.airlineservice.service.response;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.domain.Reservation;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class TicketResponse {
    private Long id;

    private String number;
    private LocalDate flightDate;

    private String passengerId;

    private FlightResponse flight;

    private String reservationCode;

    public TicketResponse(Long id, String number, LocalDate flightDate, String passengerId, FlightResponse flight, String reservationCode) {
        this.id = id;
        this.number = number;
        this.flightDate = flightDate;
        this.passengerId = passengerId;
        this.flight = flight;
        this.reservationCode = reservationCode;
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

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(LocalDate flightDate) {
        this.flightDate = flightDate;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public FlightResponse getFlight() {
        return flight;
    }

    public void setFlight(FlightResponse flight) {
        this.flight = flight;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }
}
