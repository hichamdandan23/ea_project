package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.domain.ReservationStatus;
import miu.edu.ea.airlineservice.domain.Ticket;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.repository.ReservationRepository;
import miu.edu.ea.airlineservice.repository.TicketRepository;
import miu.edu.ea.airlineservice.service.mapper.ReservationMapper;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private FlightRepository flightRepository;

    public ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById){
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.PENDING);
        // load all flights
        reservation.setFlights(flightRepository.findAllById(reservationRequest.getFlightIds()));
        reservation.setCreatedById(createdById);
        reservation.setPassengerId(reservationRequest.getPassengerId());
        reservation.setReservationCode("dd"); // go to db and read a value from a table
        return ReservationMapper.mapToReservationResponse(reservationRepository.save(reservation));
    }



    public void cancelReservation(long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                                        .orElseThrow(()->new RuntimeException("Reservation not found"));
        if(reservation.getReservationStatus().equals(ReservationStatus.PENDING))
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
        else
            throw new IllegalStateException("Reservation can't be cancelled");
        reservationRepository.save(reservation);
    }

    public List<Ticket> confirmReservation(long reservationId){
        List<Ticket> tickets = new ArrayList<>();
        //load reservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation not found"));
        if(reservation.getReservationStatus().equals(ReservationStatus.PENDING)){
            // create tickets for each flight in the reservation
            tickets = reservation.getFlights().stream().map(f -> {
                Ticket ticket = new Ticket();
                ticket.setFlight(f);
                ticket.setFlightDate(f.getDepartureTime().toLocalDate());
                ticket.setNumber("001122"); // how to generate ticket number
                ticket.setPassengerId(reservation.getPassengerId());
                return ticket;
            }).collect(Collectors.toList());
            // persist tickets
            tickets.forEach(ticket -> ticketRepository.save(ticket));
            // update reservation status
            reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        }
        else
            throw new IllegalStateException("Reservation can't be confirmed");
        return tickets;
    }
}
