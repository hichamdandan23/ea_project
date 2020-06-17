package miu.edu.ea.airlineservice.service.request;

import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReservationRequest {
    @NotNull
    private String passengerId;
    @NotNull
    private List<Long> flightIds = new ArrayList<>();

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public List<Long> getFlightIds() {
        return flightIds;
    }

    public void setFlightIds(List<Long> flightIds) {
        this.flightIds = flightIds;
    }
}
