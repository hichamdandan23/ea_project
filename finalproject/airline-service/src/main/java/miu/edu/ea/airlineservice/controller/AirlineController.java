package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.AirlineService;
import miu.edu.ea.airlineservice.service.FlightService;
import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import miu.edu.ea.airlineservice.service.AirlineService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AirlineController {
    private AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }


    @GetMapping(path = {"/passenger/airlines", "/agent/airlines", "/admin/airlines"})
    public List<AirlineResponse> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @GetMapping(path = {"/passenger/airlines", "/agent/airlines", "/admin/airlines"}, params = "code")
    public List<AirlineResponse> searchAirlineByAirportCode(@RequestParam String code){
        return airlineService.findByAirportCode(code);
    }


    @GetMapping(path = {"/passenger/airlines/{id}", "/agent/airlines/{id}", "/admin/airlines/{id}"})
    public AirlineResponse getById(@PathVariable Long id){
        return airlineService.getById(id);
    }

    @PostMapping(path = {"/admin/airlines"})
    public AirlineResponse createAirline(@Valid @RequestBody AirlineRequest airlineRequest){
        return airlineService.createAirline(airlineRequest);
    }

    @PutMapping(path = {"/admin/airlines/{id}"})
    public AirlineResponse updateAirline(@Valid @RequestBody AirlineRequest airlineRequest, @PathVariable Long id){
        return airlineService.updateAirline(airlineRequest, id);
    }

    @DeleteMapping(path = {"/admin/airlines/{id}"})
    public ResponseEntity<?> deleteAirline(@PathVariable Long id){
        airlineService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
