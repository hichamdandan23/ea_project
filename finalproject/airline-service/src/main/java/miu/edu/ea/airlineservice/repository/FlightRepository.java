package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(value = "select count(rf.reservation_id) from Reservation_Flight rf where rf.flight_id = :flightId", nativeQuery = true)
    public Long countReservations(Long flightId);
}
