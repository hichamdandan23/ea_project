package miu.edu.ea.airlineservice.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.AirlineService;
import miu.edu.ea.airlineservice.service.AirportService;
import miu.edu.ea.airlineservice.service.FlightService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    FlightService flightService;
    @Autowired
    AirportService airportService;
    @Autowired
    AirlineService airlineService;

    @GetMapping("flight/all")
    public ResponseEntity<List<Flight>> getAllFlights(){
      List<Flight> flights = flightService.getAllFlights();
      return new ResponseEntity<List<Flight>>(flights, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("flight/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id){
        Flight flight = flightService.getById(id);
        return new ResponseEntity<Flight>(flight, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("flight/post")
    public ResponseEntity<Flight> createOrUpdateFlight(@RequestBody Flight flight){
        Flight modded = flightService.createOrUpdate(flight);
        return new ResponseEntity<Flight>(modded, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("flight/delete/{id}")
    public HttpStatus deleteFlightByID (@PathVariable Long id){
        flightService.deleteById(id);
         return  HttpStatus.OK;
    }

    @GetMapping("airport/all")
    public ResponseEntity<List<Airport>> getAllAirports(){
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<List<Airport>>(airports, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("airport/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id){
        Airport airport = airportService.getById(id);
        return new ResponseEntity<Airport>(airport, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("airport/post")
    public ResponseEntity<Airport> createOrUpdateAirport(@RequestBody Airport airport){
        Airport modded = airportService.createOrUpdate(airport);
        return new ResponseEntity<Airport>(modded, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("airport/delete/{id}")
    public HttpStatus deleteAirportByID (@PathVariable Long id){
        airportService.deleteById(id);
        return  HttpStatus.OK;
    }

    @GetMapping("airline/all")
    public ResponseEntity<List<Airline>> getAllAirline(){
        List<Airline> airlines = airlineService.getAllAirlines();
        return new ResponseEntity<List<Airline>>(airlines, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("airline/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Long id){
        Airline airline = airlineService.getById(id);
        return new ResponseEntity<Airline>(airline, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("airline/post")
    public ResponseEntity<Airline> createOrUpdateFlight(@RequestBody Airline airline){
        Airline modded = airlineService.createOrUpdate(airline);
        return new ResponseEntity<Airline>(modded, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("flight/delete/{id}")
    public HttpStatus deleteAirlineByID (@PathVariable Long id){
        airlineService.deleteById(id);
        return  HttpStatus.OK;
    }

}
