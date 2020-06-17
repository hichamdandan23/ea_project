package miu.edu.ea.airlineservice.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6)
    private String reservationCode;
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;
    private String passengerId;
    private String createdById;
    @OneToMany(mappedBy = "reservation")
    private List<Ticket> tickets = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "flight_id")},
            inverseJoinColumns = {@JoinColumn(name = "reservation_id")})
    private List<Flight> flights = new ArrayList<>();
    private boolean reminded;

    public Reservation() {
    }

    public Reservation(String reservationCode, ReservationStatus reservationStatus, String passengerId, String createdById, List<Flight> flights){
        this.reservationCode = reservationCode;
        this.reservationStatus = reservationStatus;
        this.passengerId = passengerId;
        this.createdById = createdById;
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setReminded(boolean reminded) {
        this.reminded = reminded;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationCode='" + reservationCode + '\'' +
                ", reservationStatus=" + reservationStatus +
                ", passengerId='" + passengerId + '\'' +
                ", createdById='" + createdById + '\'' +
                ", tickets=" + tickets +
                ", flights=" + flights +
                ", reminded=" + reminded +
                '}';
    }
}
