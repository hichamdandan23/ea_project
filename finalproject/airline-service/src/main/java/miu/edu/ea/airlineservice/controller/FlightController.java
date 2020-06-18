package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.FlightService;

import miu.edu.ea.airlineservice.service.ReminderService;
import miu.edu.ea.airlineservice.service.response.FlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.request.FlightRequest;
import miu.edu.ea.airlineservice.service.response.AirportResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FlightController {
    private FlightService flightService;

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(path = {"/passenger/flights", "/agent/flights", "/admin/flights"})
    public List<FlightResponse> getAll() {
        return flightService.getAll();
    }

    @GetMapping(path = {"/passenger/flights", "/agent/flights", "/admin/flights"}, params = "departureAirportCode")
    public List<FlightResponse> getByArrivalAndDeparture(@RequestParam String departureAirportCode, @RequestParam String arrivalAirportCode) {
        return flightService.getByArrivalAndDeparture(departureAirportCode, arrivalAirportCode);
    }

    @GetMapping(path = {"/passenger/flights/{id}", "/agent/flights/{id}", "/admin/flights/{id}"})
    public FlightResponse getById(@PathVariable Long id){
        return flightService.getById(id);
    }

    @PostMapping(path = {"/admin/flights"})
    public FlightResponse create(@Valid @RequestBody FlightRequest flightRequest){
        return flightService.create(flightRequest);
    }

    @PutMapping(path = {"/admin/flights/{id}"})
    public FlightResponse update(@Valid @RequestBody FlightRequest flightRequest, @PathVariable Long id){
        return flightService.update(flightRequest, id);
    }

    @GetMapping(path = {"/passenger/flights", "/agent/flights"})
    public List<FlightResponse> getFlights(@RequestParam(value = "acode", required = false) String acode,
                                   @RequestParam(value = "dcode", required = false) String dcode,
                                   @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC)
                                               Pageable pageable) {
        List<FlightResponse> flights = flightService.findByAirportCode(dcode, acode, pageable).getContent();
        flights.forEach(f -> log.error("----" + f.getId()));
        return flightService.findDepartureByCodeOrArrivalByCode(dcode, acode);
    }

    @DeleteMapping(path = {"/admin/flights/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id){
        flightService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
