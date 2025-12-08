package com.example.booking_service.data;

public class CarInfo {

    private Integer id;
    private String registrationNumber;
    private String brand;
    private String model;
    private Double dailyRates;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getDailyRates() {
        return dailyRates;
    }

    public void setDailyRates(Double dailyRates) {
        this.dailyRates = dailyRates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
