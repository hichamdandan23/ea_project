package miu.edu.ea.airlineservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TicketNumber {
    @Id
    private int id;
    private String code;

    public TicketNumber() {
    }

    public TicketNumber(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
