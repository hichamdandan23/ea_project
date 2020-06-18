package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Airport;
import miu.edu.ea.airlineservice.service.AirlineService;
import miu.edu.ea.airlineservice.service.AirportService;

import miu.edu.ea.airlineservice.service.response.AirportResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import miu.edu.ea.airlineservice.service.request.AirlineRequest;
import miu.edu.ea.airlineservice.service.request.AirportRequest;
import miu.edu.ea.airlineservice.service.response.AirlineResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("airport")
public class AirportController {
    private AirportService airportService;

    @GetMapping(path = {"/passenger/airports", "/agent/airports", "/admin/airports"})
    public List<AirportResponse> getAll() {
        return airportService.getAll();
    }

    @GetMapping(path = {"/passenger/airports", "/agent/airports"})
    public List<AirportResponse> getAirports(@RequestParam("code") String code) {
        return airportService.findByCode(code);
    }

    @GetMapping(path = {"/passenger/airports/{id}", "/agent/airports/{id}", "/admin/airports/{id}"})
    public AirportResponse getById(@PathVariable Long id){
        return airportService.getById(id);
    }

    @PostMapping(path = {"/admin/airports"})
    public AirportResponse create(@Valid @RequestBody AirportRequest airportRequest){
        return airportService.create(airportRequest);
    }

    @PutMapping(path = {"/admin/airports/{id}"})
    public AirportResponse update(@Valid @RequestBody AirportRequest airportRequest, @PathVariable Long id){
        return airportService.update(airportRequest, id);
    }

    @DeleteMapping(path = {"/admin/airports/{id}"})
    public ResponseEntity<?> delete(@PathVariable Long id){
        airportService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
