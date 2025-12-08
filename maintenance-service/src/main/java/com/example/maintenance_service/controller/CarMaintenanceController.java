package com.example.maintenance_service.controller;

import com.example.maintenance_service.data.CarMaintenance;
import com.example.maintenance_service.service.CarMaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
public class CarMaintenanceController {

    private final CarMaintenanceService service;

    public CarMaintenanceController(CarMaintenanceService service) {
        this.service = service;
    }

    // CREATE maintenance record
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CarMaintenance cm) {
        try {
            CarMaintenance saved = service.addRecord(cm);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("message", ex.getMessage())
            );
        }
    }


    // GET ALL
    @GetMapping
    public ResponseEntity<List<CarMaintenance>> getAll() {
        return ResponseEntity.ok(service.getAllRecords());
    }

    // GET ONE
    @GetMapping("/{id}")
    public ResponseEntity<CarMaintenance> getById(@PathVariable Long id) {
        return service.getRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE record
    @PutMapping("/{id}")
    public ResponseEntity<CarMaintenance> update(
            @PathVariable Long id,
            @RequestBody CarMaintenance cm
    ) {
        return service.updateRecord(id, cm)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // COMPLETE maintenance
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        CarMaintenance updated = service.completeMaintenance(id);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // DELETE record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
