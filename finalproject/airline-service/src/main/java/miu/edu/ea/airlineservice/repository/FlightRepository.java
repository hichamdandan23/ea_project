package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.arrival.code = :arrival_code OR f.departure.code = :departure_code")
    List<Flight> findByDepartureOrArrival(@Param("departure_code") String Dcode, @Param("arrival_code") String Acode);

    @Query(value = "select count(rf.reservation_id) from Reservation_Flight rf where rf.flight_id = :flightId", nativeQuery = true)
    public Long countReservations(Long flightId);
}
