package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
}
