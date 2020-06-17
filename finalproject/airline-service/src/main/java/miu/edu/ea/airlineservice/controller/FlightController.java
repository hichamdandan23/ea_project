package miu.edu.ea.airlineservice.controller;

import miu.edu.ea.airlineservice.domain.Flight;
import miu.edu.ea.airlineservice.service.FlightService;
import miu.edu.ea.airlineservice.service.ReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {
    private FlightService flightService;

    private static final Logger log = LoggerFactory.getLogger(FlightController.class);

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @GetMapping(path = {"/passenger/flights", "/agent/flights"})
    public List<Flight> getFlights(@RequestParam(value = "acode", required = false) String acode,
                                   @RequestParam(value = "dcode", required = false) String dcode,
                                   @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC)
                                               Pageable pageable) {
        List<Flight> flights = flightService.findByAirportCode(dcode, acode, pageable).getContent();
        flights.forEach(f -> log.error("----" + f.getId()));
        return flightService.findDepartureByCodeOrArrivalByCode(dcode, acode);
    }
}
