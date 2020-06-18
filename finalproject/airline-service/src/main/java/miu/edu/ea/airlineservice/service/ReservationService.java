package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById);
    void cancelReservation(long reservationId);
    List<TicketResponse> confirmReservation(long reservationId);

    List<ReservationResponse> getAllReservations();
    List<ReservationResponse> getAllPassengerReservations(String userId);
    ReservationResponse getPassengerReservation(Long id, String userId);
    List<ReservationResponse> getAllAgentReservations(String createdById);
    ReservationResponse getAgentReservation(Long id, String createdById);

}
