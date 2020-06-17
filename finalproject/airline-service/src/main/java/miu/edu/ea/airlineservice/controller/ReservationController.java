package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Ticket;
import miu.edu.ea.airlineservice.service.ReservationServiceImpl;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    public ReservationController(@Qualifier("reservationServiceImpl") ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping(path = {"/admin/reservations", "/passenger/reservations", "/agent/reservations"})
    public ReservationResponse createReservation(@Valid @RequestBody ReservationRequest reservationRequest, @RequestHeader(value = "USER_ID", defaultValue = "0") String userId) {
        return reservationService.createReservation(reservationRequest, userId);
    }

    @PutMapping(path = {"/admin/reservations/{id}/cancel}", "/passenger/reservations/{id}/cancel", "/agent/reservations/{id}/cancel"})
    public void cancelReservation(@PathVariable long id){
        reservationService.cancelReservation(id);
    }


    @PutMapping(path = {"/admin/reservations/{id}/confirm}", "/passenger/reservations/{id}/confirm", "/agent/reservations/{id}/confirm"})
    public List<TicketResponse> confirmReservation(@PathVariable long id){
        return reservationService.confirmReservation(id);
    }
}
