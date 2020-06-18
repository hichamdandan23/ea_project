package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.service.ReservationServiceImpl;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReservationController {
    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationServiceImpl reservationService;

    public ReservationController(@Qualifier("reservationServiceImpl") ReservationServiceImpl reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(path = {"/admin/reservations", "/passenger/reservations", "/agent/reservations"})
    public List<ReservationResponse> listReservations(@RequestHeader(value = "USER_ID", defaultValue = "0") String userId, @RequestHeader(value = "USER_ROLE", defaultValue = "0") String roles){
        return reservationService.getAllReservations(userId, roles);
    }

    @GetMapping(path = {"/admin/reservations/{id}", "/passenger/reservations/{id}", "/agent/reservations/{id}"})
    public ReservationResponse getReservatonById(@PathVariable Long id, @RequestHeader(value = "USER_ID", defaultValue = "0") String userId, @RequestHeader(value = "USER_ROLE", defaultValue = "0") String roles){
        return reservationService.getById(id, userId, roles);
    }

    @GetMapping("/admin/reservations")
    public List<ReservationResponse> createReservation(){
        return reservationService.getAllReservations();
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
