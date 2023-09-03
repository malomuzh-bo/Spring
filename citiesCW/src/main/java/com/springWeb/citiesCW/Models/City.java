package com.springWeb.citiesCW.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String cityName = "";
    private Integer personAmo;
    private String info = "";
    private long latitude;
    private long longitude;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public City() {
    }

    public City(String cityName, Integer personAmo, String info, long latitude, long longitude) {
        this.cityName = cityName;
        this.personAmo = personAmo;
        this.info = info;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getPersonAmo() {
        return personAmo;
    }

    public void setPersonAmo(Integer personAmo) {
        this.personAmo = personAmo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }
}
