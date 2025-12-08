package com.example.return_service.data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_return")
public class VehicleReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;
    private Long customerId;
    private Long carId;

    private String customerName;
    private String vehicleRegNo;

    private Integer odometerAtPickup;
    private Integer odometerAtReturn;
    private Integer runnedMileage;

    private Boolean damaged;
    private String damageDescription;

    private LocalDateTime returnedAt;

    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public Integer getOdometerAtPickup() {
        return odometerAtPickup;
    }

    public void setOdometerAtPickup(Integer odometerAtPickup) {
        this.odometerAtPickup = odometerAtPickup;
    }

    public Integer getOdometerAtReturn() {
        return odometerAtReturn;
    }

    public void setOdometerAtReturn(Integer odometerAtReturn) {
        this.odometerAtReturn = odometerAtReturn;
    }

    public Integer getRunnedMileage() {
        return runnedMileage;
    }

    public void setRunnedMileage(Integer runnedMileage) {
        this.runnedMileage = runnedMileage;
    }

    public Boolean getDamaged() {
        return damaged;
    }

    public void setDamaged(Boolean damaged) {
        this.damaged = damaged;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
