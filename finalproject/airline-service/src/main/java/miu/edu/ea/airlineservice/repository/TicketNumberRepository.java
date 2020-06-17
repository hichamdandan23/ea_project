package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.TicketNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketNumberRepository extends JpaRepository<TicketNumber, Integer> {
}
