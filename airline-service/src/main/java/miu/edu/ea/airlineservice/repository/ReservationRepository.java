package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByPassengerId(String passengerId);
    Reservation findByPassengerIdAndAndId(String passengerId, Long id);
    List<Reservation> findAllByCreatedById(String createdById);
    Reservation findAllByCreatedByIdAndAndId(String createdById, Long id);
}
