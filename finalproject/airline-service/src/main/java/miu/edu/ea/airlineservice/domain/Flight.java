package miu.edu.ea.airlineservice.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Flight {
    public Flight() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    public Airport getArrival() {
        return arrival;
    }

    public void setArrival(Airport arrival) {
        this.arrival = arrival;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private Airport departure;

    @ManyToOne
    @JoinColumn(name = "arrival_id")
    private Airport arrival;

    @ManyToMany(mappedBy = "flights")
    private List<Reservation> reservations;
}
