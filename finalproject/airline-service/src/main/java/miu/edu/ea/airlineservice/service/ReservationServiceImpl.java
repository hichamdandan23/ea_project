package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.Reservation;
import miu.edu.ea.airlineservice.domain.TicketNumber;
import miu.edu.ea.airlineservice.domain.ReservationStatus;
import miu.edu.ea.airlineservice.domain.Ticket;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.repository.TicketNumberRepository;
import miu.edu.ea.airlineservice.repository.ReservationRepository;
import miu.edu.ea.airlineservice.repository.TicketRepository;
import miu.edu.ea.airlineservice.service.mapper.ReservationMapper;
import miu.edu.ea.airlineservice.service.mapper.TicketMapper;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("reservationServiceImpl")
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private ReservationRepository reservationRepository;
    private TicketRepository ticketRepository;
    private FlightRepository flightRepository;
    private TicketNumberRepository ticketNumberRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TicketRepository ticketRepository, FlightRepository flightRepository, TicketNumberRepository ticketNumberRepository) {
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.ticketNumberRepository = ticketNumberRepository;
    }


    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById){
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.PENDING);
        // load all flights
        reservation.setFlights(flightRepository.findAllById(reservationRequest.getFlightIds()));
        reservation.setCreatedById(createdById);
        reservation.setPassengerId(reservationRequest.getPassengerId());
        reservation.setReservationCode(UUID.randomUUID().toString().substring(0,4));
        return ReservationMapper.mapToReservationResponse(reservationRepository.save(reservation));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public String getNextTicketNumber(){
        TicketNumber flightNumber = null;
        Optional<TicketNumber> resCode = ticketNumberRepository.findById(1);
        if(resCode.isPresent()){
            flightNumber = resCode.get();
        } else
            flightNumber = new TicketNumber(1, "00000000000000000001");

        BigInteger b = BigInteger.valueOf(Long.parseLong(flightNumber.getCode()));
        b.add(BigInteger.ONE);
        flightNumber.setCode(padLeftZeros(b.toString(), 20));
        return ticketNumberRepository.save(flightNumber).getCode();
    }

    @Override
    public void cancelReservation(long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                                        .orElseThrow(()->new RuntimeException("Reservation not found"));
        if(reservation.getReservationStatus().equals(ReservationStatus.PENDING))
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
        else
            throw new IllegalStateException("Reservation can't be cancelled");
        reservationRepository.save(reservation);
    }

    @Override
    public List<TicketResponse> confirmReservation(long reservationId){
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
                ticket.setNumber(getNextTicketNumber()); // how to generate ticket number
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
        return tickets.stream().map(ticket-> TicketMapper.mapToTicketResponse(ticket)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
