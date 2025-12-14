package com.example.user_service.controller;

import com.example.user_service.data.User;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        User user = service.login(data.get("username"), data.get("password"));

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        return ResponseEntity.ok(user);
    }

    // SELF REGISTER (CUSTOMER)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }

    // ADMIN manually adds customer
    @PostMapping("/add")
    public ResponseEntity<?> addManualCustomer(@RequestBody User user) {
        return ResponseEntity.ok(service.addCustomerByAdmin(user));
    }

    // Admin update user
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        User updated = service.updateUser(id, user);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteUser(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // Get all users
    @GetMapping
    public List<User> allUsers() {
        return service.getAll();
    }


    // Get user by ID (required by booking-service)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
