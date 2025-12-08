package com.example.maintenance_service.data;

import jakarta.persistence.*;

@Entity
@Table(name = "car_maintenance")
public class CarMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer carId; // car id from car-service
    private String vehicleRegisterNumber;
    private String vehicleType;
    private String serviceType;
    private String serviceDescription;
    private Integer noOfDaysInService;
    private String personInCharge;
    private Double totalCost;

    private String status; // IN_PROGRESS / COMPLETED

    public CarMaintenance() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getVehicleRegisterNumber() {
        return vehicleRegisterNumber;
    }

    public void setVehicleRegisterNumber(String vehicleRegisterNumber) {
        this.vehicleRegisterNumber = vehicleRegisterNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Integer getNoOfDaysInService() {
        return noOfDaysInService;
    }

    public void setNoOfDaysInService(Integer noOfDaysInService) {
        this.noOfDaysInService = noOfDaysInService;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
