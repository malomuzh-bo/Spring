package com.springGroup.rentingDB.Models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Landlord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String username = "", pass = "";

    public Landlord() {}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public Landlord(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Landlord(String username, String pass, Apartment apartment) {
        this.username = username;
        this.pass = pass;
        this.apartment = apartment;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
