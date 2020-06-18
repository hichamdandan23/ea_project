package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ReservationService {
    List<ReservationResponse> getAllReservations(String userId, String userRoles);
    ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById);
    void cancelReservation(long reservationId);
    List<TicketResponse> confirmReservation(long reservationId);

    List<ReservationResponse> getReservationsByCreator(Long creatorId, Pageable pageable);
    List<ReservationResponse> getReservationsByPassenger(Long passengerId, Pageable pageable);
    ReservationResponse getReservationDetail(Long reservationId);

    List<ReservationResponse> getAllReservations();
    List<ReservationResponse> getAllPassengerReservations(String userId);
    ReservationResponse getPassengerReservation(Long id, String userId);
    List<ReservationResponse> getAllAgentReservations(String createdById);
    ReservationResponse getAgentReservation(Long id, String createdById);
}
