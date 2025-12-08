package com.example.return_service.service;

import com.example.return_service.data.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class VehicleReturnService {

    private final VehicleReturnRepository repo;
    private final RestTemplate rest;

    // Microservice URLs
    private final String BOOKING_URL = "http://localhost:8084/api/bookings/";     // booking-service
    private final String CAR_URL = "http://localhost:8083/api/cars/";             // car-service
    private final String USER_URL = "http://localhost:8082/api/user/";            // user-service
    private final String MAINT_URL = "http://localhost:8085/api/maintenance";     // maintenance-service

    public VehicleReturnService(VehicleReturnRepository repo, RestTemplate rest) {
        this.repo = repo;
        this.rest = rest;
    }

    // Create return record
    public VehicleReturn create(VehicleReturn r) {

        // Get booking information
        BookingInfo booking = rest.getForObject(
                BOOKING_URL + r.getBookingId(),
                BookingInfo.class
        );

        if (booking == null) {
            throw new RuntimeException("Booking not found in booking-service");
        }

        // Get car information
        CarInfo car = rest.getForObject(
                CAR_URL + r.getCarId(),
                CarInfo.class
        );

        if (car == null) {
            throw new RuntimeException("Car not found in car-service");
        }

        // Get user information
        UserInfo user = rest.getForObject(
                USER_URL + booking.getUserId(),
                UserInfo.class
        );

        if (user == null) {
            throw new RuntimeException("User not found in user-service");
        }

        // ===== 4. SET AUTO-FILLED FIELDS =====
        r.setCustomerId(booking.getUserId());       // from booking-service
        r.setCustomerName(user.getUsername());      // from user-service
        r.setVehicleRegNo(car.getRegistrationNumber());

        // MILEAGE CALCULATION
        int mileage = r.getOdometerAtReturn() - r.getOdometerAtPickup();
        if (mileage < 0) {
            throw new RuntimeException("Invalid mileage values");
        }
        r.setRunnedMileage(mileage);

        // SET RETURN DATE
        r.setReturnedAt(LocalDateTime.now());

        // IF DAMAGED,SEND TO MAINTENANCE
        if (Boolean.TRUE.equals(r.getDamaged())) {

            MaintenanceRequest req = new MaintenanceRequest();
            req.setCarId(r.getCarId().intValue());
            req.setVehicleRegisterNumber(car.getRegistrationNumber());
            req.setServiceType("RETURN_DAMAGE");
            req.setServiceDescription(r.getDamageDescription());
            req.setStatus("PENDING");

            try {
                rest.postForObject(MAINT_URL, req, Void.class);
            } catch (Exception ex) {
                System.out.println("Warning: Could not send to maintenance-service");
            }
        }

        // SAVE RETURN RECORD
        return repo.save(r);
    }

    // GET ALL
    public java.util.List<VehicleReturn> getAll() {
        return repo.findAll();
    }

    //  GET BY ID
    public VehicleReturn getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Return record not found"));
    }

    //  DELETE
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Record not found");
        }
        repo.deleteById(id);
    }
}
