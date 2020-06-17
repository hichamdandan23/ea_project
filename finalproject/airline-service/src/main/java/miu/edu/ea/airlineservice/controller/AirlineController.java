package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Airline;
import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.AirlineService;
import miu.edu.ea.airlineservice.service.FlightService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import miu.edu.ea.airlineservice.service.AirlineService;

import java.util.List;

@RestController
@RequestMapping("airline")
public class AirlineController {
    private AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }


    @GetMapping(path = {"/passenger/airlines", "/agent/airlines"})
    public List<Airline> getAirlines(@RequestParam("code") String code) {
        return airlineService.findByCode(code);
    }
}
