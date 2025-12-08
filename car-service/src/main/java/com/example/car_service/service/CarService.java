package com.example.car_service.service;

import com.example.car_service.data.Car;
import com.example.car_service.data.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository repo;

    public CarService(CarRepository repo) {
        this.repo = repo;
    }

    // Get all cars
    public List<Car> getAllCars() {
        return repo.findAll();
    }

    // Get one car by ID
    public Car getCar(Long id) {

        Optional<Car> optionalCar = repo.findById(id);

        if (optionalCar.isPresent()) {
            return optionalCar.get();
        } else {
            throw new RuntimeException("Car not found with ID: " + id);
        }
    }

    // Create new car
    public Car createCar(Car car) {

        if (repo.existsByRegistrationNumber(car.getRegistrationNumber())) {
            throw new RuntimeException("Car already registered");
        }

        // Default initial status
        if (car.getStatus() == null || car.getStatus().isEmpty()) {
            car.setStatus("AVAILABLE");
        }

        return repo.save(car);
    }

    // Update existing car
    public Car updateCar(Long id, Car updatedCar) {

        Car car = getCar(id);  // will throw exception if not found

        car.setRegistrationNumber(updatedCar.getRegistrationNumber());
        car.setBrand(updatedCar.getBrand());
        car.setModel(updatedCar.getModel());
        car.setSeats(updatedCar.getSeats());
        car.setDailyRates(updatedCar.getDailyRates());
        car.setStatus(updatedCar.getStatus());
        car.setMileage(updatedCar.getMileage());
        car.setFuelType(updatedCar.getFuelType());
        car.setTransmission(updatedCar.getTransmission());

        return repo.save(car);
    }

    // Delete car
    public void deleteCar(Long id) {
        repo.deleteById(id);
    }


    // Update only car status (used by booking/maintenance services)
    public Car updateCarStatus(Long id, String newStatus) {

        Optional<Car> optionalCar = repo.findById(id);

        if (!optionalCar.isPresent()) {
            return null; // controller will handle "not found"
        }

        Car car = optionalCar.get();
        car.setStatus(newStatus);

        return repo.save(car);
    }
}
