package miu.edu.ea.airlineservice.service.mapper;

import miu.edu.ea.airlineservice.domain.Ticket;
import miu.edu.ea.airlineservice.service.response.TicketResponse;

public class TicketMapper {
    public static TicketResponse mapToTicketResponse(Ticket ticket){
      TicketResponse ticketResponse = new TicketResponse();
      ticketResponse.setId(ticket.getId());
      ticketResponse.setNumber(ticket.getNumber());
      ticketResponse.setFlightDate(ticket.getFlightDate());
      ticketResponse.setPassengerId(ticket.getPassengerId());
      ticketResponse.setFlight(FlightMapper.mapToFlightResponse(ticket.getFlight()));
      ticketResponse.setReservationCode(ticket.getReservation().getReservationCode());
      return ticketResponse;
    }
}
