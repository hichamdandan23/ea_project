package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

    @Query("SELECT a FROM Flight f join f.airline a join f.departure ap WHERE ap.code = :airport_code")
    List<Airline> findByAirportCode(@Param("airport_code") String code);

}
