package com.example.maintenance_service.service;

import com.example.maintenance_service.data.CarMaintenance;
import com.example.maintenance_service.data.CarMaintenanceRepository;
import com.example.maintenance_service.data.CarInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CarMaintenanceService {

    private final CarMaintenanceRepository repository;
    private final RestTemplate restTemplate;

    private final String CAR_SERVICE_BASE = "http://localhost:8083/api/cars/";

    public CarMaintenanceService(CarMaintenanceRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // CREATE
    public CarMaintenance addRecord(CarMaintenance cm) {

        // Validate carId
        if (cm.getCarId() == null) {
            throw new RuntimeException("carId is required");
        }

        // Validate car exists in car-service
        CarInfo car;
        try {
            car = restTemplate.getForObject(
                    CAR_SERVICE_BASE + cm.getCarId(),
                    CarInfo.class
            );
        } catch (Exception ex) {
            throw new RuntimeException("Car not found in car-service");
        }

        if (car == null) {
            throw new RuntimeException("Car not found in car-service");
        }

        // Auto-fill registration number from car-service
        cm.setVehicleRegisterNumber(car.getRegistrationNumber());

        return repository.save(cm);
    }

    // READ ALL
    public List<CarMaintenance> getAllRecords() {
        return repository.findAll();
    }

    // READ BY ID
    public Optional<CarMaintenance> getRecordById(Long id) {
        return repository.findById(id);
    }

    // UPDATE
    public Optional<CarMaintenance> updateRecord(Long id, CarMaintenance cm) {

        return repository.findById(id).map(existing -> {

            // Validate car
            if (cm.getCarId() != null) {
                CarInfo car;
                try {
                    car = restTemplate.getForObject(
                            CAR_SERVICE_BASE + cm.getCarId(),
                            CarInfo.class
                    );
                } catch (Exception ex) {
                    throw new RuntimeException("Car not found in car-service");
                }

                existing.setCarId(cm.getCarId());
                existing.setVehicleRegisterNumber(car.getRegistrationNumber());
            }

            existing.setVehicleType(cm.getVehicleType());
            existing.setServiceType(cm.getServiceType());
            existing.setServiceDescription(cm.getServiceDescription());
            existing.setNoOfDaysInService(cm.getNoOfDaysInService());
            existing.setPersonInCharge(cm.getPersonInCharge());
            existing.setTotalCost(cm.getTotalCost());
            existing.setStatus(cm.getStatus());

            return repository.save(existing);
        });
    }

    public CarMaintenance completeMaintenance(Long id) {

        Optional<CarMaintenance> optional = repository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Maintenance record not found");
        }

        CarMaintenance record = optional.get();
        record.setStatus("COMPLETED");

        return repository.save(record);
    }


    // DELETE
    public void deleteRecord(Long id) {
        repository.deleteById(id);
    }

}
