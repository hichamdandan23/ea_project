package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.service.response.FlightResponse;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationMapper {
    public static ReservationResponse mapToReservationResponse(Reservation reservation){
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setPassengerId(reservation.getPassengerId());
        reservationResponse.setCreatedById(reservation.getCreatedById());
        reservationResponse.setReservationStatus(reservation.getReservationStatus());
        reservationResponse.setReservationCode(reservation.getReservationCode());
        List<FlightResponse> flightResponses = reservation.getFlights().stream().map(FlightMapper::mapToFlightResponse).collect(Collectors.toList());
        reservationResponse.setFlights(flightResponses);
        return reservationResponse;
    }

}
