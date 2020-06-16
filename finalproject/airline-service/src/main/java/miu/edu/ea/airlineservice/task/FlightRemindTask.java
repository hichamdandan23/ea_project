package miu.edu.ea.airlineservice.task;

import org.springframework.scheduling.annotation.Scheduled;

public class FlightRemindTask {
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void flightRemind() {

    }
}
