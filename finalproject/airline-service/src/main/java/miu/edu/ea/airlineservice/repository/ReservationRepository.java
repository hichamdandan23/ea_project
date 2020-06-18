package miu.edu.ea.airlineservice.repository;

import edu.miu.ea.commons.repository.BaseRepository;
import miu.edu.ea.airlineservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public interface ReservationRepository extends BaseRepository<Reservation, Long> {


    @Query("select distinct r from Reservation r join r.flights f"
            +" where r.reservationStatus='CONFIRMED'"
            +" and r.reminded=false"
            +" and f.departureTime < ?1")
    List<Reservation> findReservationsNeedRemind(LocalDateTime time);


    List<Reservation> findAllByPassengerId(String passengerId);
    Reservation findByPassengerIdAndAndId(String passengerId, Long id);
    List<Reservation> findAllByCreatedById(String createdById);
    Reservation findAllByCreatedByIdAndAndId(String createdById, Long id);
}
