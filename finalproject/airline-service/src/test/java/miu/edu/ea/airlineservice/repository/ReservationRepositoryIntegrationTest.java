package miu.edu.ea.airlineservice.repository;

import miu.edu.ea.airlineservice.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReservationRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    private final Airline testAirline1 = new Airline(1L, "UTD", "United Airlines", "Long history");
    private final Address testAddress1 = new Address("Street1", "City1", "State1", "1111");
    private final Airport testAirport1 = new Airport("JFK", "John F Kennedy Int Airport", testAddress1);
    private final Airport testAirport2 = new Airport("NYK", "New York Int Airport", testAddress1);
    private final Flight testFlight1 = new Flight("2332", 100, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), testAirport1, testAirport2);
    private final Reservation reservation = new Reservation("wdeded", ReservationStatus.PENDING, "11", "11", Arrays.asList(testFlight1));

    @BeforeEach
    public void setUp(){

        testEntityManager.persist(testAirline1);
        testEntityManager.persist(testAddress1);
        testEntityManager.persist(testAirport1);
        testEntityManager.persist(testAirport2);
        testEntityManager.persist(testFlight1);
        testEntityManager.persist(reservation);
        testEntityManager.flush();

    }

    @AfterEach
    public void cleanUp(){
        testEntityManager.remove(testAirline1);
        testEntityManager.remove(testAddress1);
        testEntityManager.remove(testAirport1);
        testEntityManager.remove(testAirport2);
        testEntityManager.remove(testFlight1);
        testEntityManager.remove(reservation);
        testEntityManager.flush();

    }

    @Test
    public void testFindAllReturnsAllReservations(){

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations, hasItem(hasProperty("reservationCode", is(reservation.getReservationCode()))));
        assertThat(reservations, hasItem(hasProperty("createdById", is(reservation.getCreatedById()))));
        assertThat(reservations, hasItem(hasProperty("passengerId", is(reservation.getPassengerId()))));

    }
//
//    @Test
//    public void testFindCountriesFilteredByName(){
//
//        assertTrue(countryRepository.findAllByNameContaining("1").stream().allMatch(country -> country.getName().contains("1")));
//
//        assertTrue(countryRepository.findAllByNameContaining("2").stream().allMatch(country -> country.getName().contains("2")));
//
//        assertThat(countryRepository.findAllByNameContaining("3"), empty());
//
//        assertThat(countryRepository.findAllByNameContaining("1"), not(empty()));
//
//        assertThat(countryRepository.findAllByNameContaining("2"), not(empty()));
//
//    }

}
