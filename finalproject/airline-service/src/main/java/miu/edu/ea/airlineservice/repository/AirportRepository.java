package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT a FROM Airport a WHERE a.code = :airport_code")
    List<Airport> findByCode(@Param("airport_code") String code);
}
