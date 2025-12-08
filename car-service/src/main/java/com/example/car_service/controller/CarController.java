package com.example.car_service.controller;

import com.example.car_service.data.Car;
import com.example.car_service.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

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

    // Update only car status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestParam String value) {

        Car updated = service.updateCarStatus(id, value);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }
}
