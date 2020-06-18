package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;

import java.util.List;

public interface ReservationService {
    List<ReservationResponse> getAllReservations(String userId, String userRoles);
    ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById);
    void cancelReservation(long reservationId);
    List<TicketResponse> confirmReservation(long reservationId);
}
