package com.example.return_service.controller;

import com.example.return_service.data.VehicleReturn;
import com.example.return_service.service.VehicleReturnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class VehicleReturnController {

    private final VehicleReturnService service;

    public VehicleReturnController(VehicleReturnService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VehicleReturn> create(@RequestBody VehicleReturn r) {
        return ResponseEntity.ok(service.create(r));
    }

    @GetMapping
    public List<VehicleReturn> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleReturn> getOne(@PathVariable Long id) {
        VehicleReturn r = service.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
