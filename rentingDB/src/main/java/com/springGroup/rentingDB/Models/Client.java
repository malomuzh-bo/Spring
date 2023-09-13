package com.springGroup.rentingDB.Models;

import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String username = "";
    private String pass = "";

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client(String username, String pass, String number) {
        this.username = username;
        this.pass = pass;
        this.number = number;
    }

    private String number = "";
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rent_id", referencedColumnName = "id")
    private Rent rent;

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Client(String username, String pass, Apartment apartment) {
        this.username = username;
        this.pass = pass;
        this.apartment = apartment;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;


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

    public Client(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public Client() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
