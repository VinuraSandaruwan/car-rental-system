package com.example.car_service.controller;

import com.example.car_service.data.Car;
import com.example.car_service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cars")

public class CarController {
    @Autowired
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    // Get all cars
    @GetMapping
    public List<Car> getAllCars() {
        return service.getAllCars();
    }

    // Get one car by ID
    @GetMapping("/{id}")
    public Car getOneCar(@PathVariable Long id) {
        return service.getCar(id);
    }

    // Create car
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return service.createCar(car);
    }

    // Update full car object
    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        return service.updateCar(id, car);
    }

    // Delete car
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        service.deleteCar(id);
    }

    //Update status from other microservices
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestParam String value) {

        Car updated = service.updateCarStatus(id, value);

        return ResponseEntity.ok(updated);
    }


}
