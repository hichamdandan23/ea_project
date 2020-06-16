package miu.edu.ea.airlineservice.task;

import miu.edu.ea.airlineservice.service.ReminderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlightRemindTask {
    @Autowired
    private ReminderService reminderService;

    private static final Logger log = LoggerFactory.getLogger(FlightRemindTask.class);

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void flightRemind() {
        reminderService.sendFlightReminder();
    }
}
