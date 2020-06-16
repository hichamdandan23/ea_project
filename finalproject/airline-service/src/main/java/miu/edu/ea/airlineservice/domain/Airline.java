package miu.edu.ea.airlineservice.domain;

import javax.persistence.*;

@Entity
@SecondaryTable(name="airline_history")
public class Airline {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String name;

    @Column(length = 2000, table = "airline_history")
    private String history;

    public Airline(Long id, String code, String name, String history) {
        this.code = code;
        this.history = history;
        this.name = name;
        this.history = history;
    }


    public Airline() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

}
