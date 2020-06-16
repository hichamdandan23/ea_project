package miu.edu.ea.airlineservice.domain;

import javax.persistence.*;

@Entity
public class Airport {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 3, nullable = false)
    private String code;

    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Airport() {
    }

    public Airport (String code, String name, Address address) {
        this.code = code;
        this.name = name;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
