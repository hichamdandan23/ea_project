package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById);
    void cancelReservation(long reservationId);
    List<TicketResponse> confirmReservation(long reservationId);

    List<ReservationResponse> getReservationsByCreator(Long creatorId, Pageable pageable);
    List<ReservationResponse> getReservationsByPassenger(Long passengerId, Pageable pageable);
    ReservationResponse getReservationDetail(Long reservationId);
}
