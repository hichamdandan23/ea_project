package miu.edu.ea.airlineservice.service;

import miu.edu.ea.airlineservice.domain.*;
import miu.edu.ea.airlineservice.exception.ApiCustomException;
import miu.edu.ea.airlineservice.repository.FlightRepository;
import miu.edu.ea.airlineservice.repository.TicketNumberRepository;
import miu.edu.ea.airlineservice.repository.ReservationRepository;
import miu.edu.ea.airlineservice.repository.TicketRepository;
import miu.edu.ea.airlineservice.service.mapper.ReservationMapper;
import miu.edu.ea.airlineservice.service.mapper.TicketMapper;
import miu.edu.ea.airlineservice.service.request.ReservationRequest;
import miu.edu.ea.airlineservice.service.response.ReservationResponse;
import miu.edu.ea.airlineservice.service.response.TicketResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ReservationResponse createReservation(ReservationRequest reservationRequest, String createdById) {
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(ReservationStatus.PENDING);
        // load all flights
        for (Long i : reservationRequest.getFlightIds()) {
            // load flight
            Flight flight = flightRepository.findById(i).orElseThrow(() -> new ApiCustomException("Flight with id " + i +" is not found"));
            // check if flight has space
            if( !(flightRepository.countReservations(i) < flight.getCapacity()) )
                throw new ApiCustomException("Flight with id " + flight.getId() + " is full!");
            reservation.getFlights().add(flight);
        }
        reservation.setCreatedById(createdById);
        reservation.setPassengerId(reservationRequest.getPassengerId());
        reservation.setReservationCode(UUID.randomUUID().toString().substring(0, 4).toUpperCase());
        return ReservationMapper.mapToReservationResponse(reservationRepository.save(reservation));
    }


    @Override
    public void cancelReservation(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiCustomException("Reservation with id "+reservationId+" is not found"));
        if (reservation.getReservationStatus().equals(ReservationStatus.PENDING))
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
        else
            throw new ApiCustomException("Only Reservations with PENDING state can be cancelled");
        reservationRepository.save(reservation);
    }

    @Override
    public List<TicketResponse> confirmReservation(long reservationId) {
        List<Ticket> tickets = new ArrayList<>();
        //load reservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiCustomException("Reservation with id "+reservationId+" is not found"));
        if (reservation.getReservationStatus().equals(ReservationStatus.PENDING)) {
            // create tickets for each flight in the reservation
            tickets = reservation.getFlights().stream().map(f -> {
                Ticket ticket = new Ticket();
                ticket.setFlight(f);
                ticket.setFlightDate(f.getDepartureTime().toLocalDate());
                ticket.setNumber(getNextTicketNumber()); // how to generate ticket number
                ticket.setPassengerId(reservation.getPassengerId());
                ticket.setReservation(reservation);
                return ticket;
            }).collect(Collectors.toList());
            // persist tickets
            ticketRepository.saveAll(tickets);
            // update reservation status
            reservation.setReservationStatus(ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);
        } else
            throw new ApiCustomException("Only Reservations with PENDING state can be confirmed");
        return tickets.stream().map(ticket -> TicketMapper.mapToTicketResponse(ticket)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public String getNextTicketNumber() {
        TicketNumber flightNumber = null;
        Optional<TicketNumber> resCode = ticketNumberRepository.findById(1);
        if (resCode.isPresent()) {
            flightNumber = resCode.get();
        } else
            flightNumber = new TicketNumber(1, "00000000000000000000");

        BigInteger b = BigInteger.valueOf(Long.parseLong(flightNumber.getCode()));

        flightNumber.setCode(padLeftZeros(b.add(BigInteger.ONE).toString(), 20));
        return ticketNumberRepository.save(flightNumber).getCode();
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

    @Override
    public List<ReservationResponse> getReservationsByCreator(Long creatorId, Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll(new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("createdById"), creatorId.toString()));
                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                        .getRestriction();
            }
        }, pageable).getContent();
        return reservations.stream()
                .map(reservation -> ReservationMapper.mapToReservationResponse(reservation))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getReservationsByPassenger(Long passengerId, Pageable pageable) {
        List<Reservation> reservations = reservationRepository.findAll(new Specification<Reservation>() {
            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("passengerId"), passengerId.toString()));
                return  criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))
                        .getRestriction();
            }
        }, pageable).getContent();
        return reservations.stream()
                .map(reservation -> ReservationMapper.mapToReservationResponse(reservation))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse getReservationDetail(Long reservationId) {
        return ReservationMapper.mapToReservationResponse(
                reservationRepository.findById(reservationId).orElse(null));
    }
}
