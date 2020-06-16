package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Ticket;
import miu.edu.ea.airlineservice.service.response.TicketResponse;

public class TicketMapper {
    public static TicketResponse mapToTicketResponse(Ticket ticket){
      return new TicketResponse(ticket.getId(),
                ticket.getNumber(),
                ticket.getFlightDate(),
                ticket.getPassengerId(),
                FlightMapper.mapToFlightResponse(ticket.getFlight()),
                ticket.getReservation().getReservationCode());
    }
}
