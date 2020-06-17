package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("airline")
public class FlightController {
    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping
    public List<Flight> getFlights(@RequestParam("acode") String acode, @RequestParam("dcode") String dcode) {
        return flightService.findDepartureByCodeOrArrivalByCode(dcode, acode);
    }
}
