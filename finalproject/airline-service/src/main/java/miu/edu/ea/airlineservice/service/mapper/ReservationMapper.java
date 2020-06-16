package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;

public class ReservationMapper {
    public static ReservationResponse mapToReservationResponse(Reservation reservation){
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setPassengerId(reservation.getPassengerId());
        reservationResponse.setCreatedById(reservation.getCreatedById());
        reservationResponse.setReservationStatus(reservation.getReservationStatus());
        reservation.getFlights().forEach(FlightMapper::mapToFlightResponse);
        return reservationResponse;
    }

}
