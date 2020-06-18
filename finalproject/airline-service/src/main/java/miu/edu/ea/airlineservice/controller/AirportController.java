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

import java.util.List;

@RestController
@RequestMapping("airport")
public class AirportController {
    private AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }


    @GetMapping(path = {"/passenger/airports", "/agent/airports"})
    public List<AirportResponse> getAirports(@RequestParam("code") String code) {
        return airportService.findByCode(code);
    }
}
