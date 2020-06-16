package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    /*
    @Query("from Reservation r join r.flights f order by f.departureTime asc limit 1")
    public List<Reservation> findReservationsNeedRemind();
     */
}
