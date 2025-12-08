package com.example.user_service.service;

import com.example.user_service.data.User;
import com.example.user_service.data.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // LOGIN
    public User login(String username, String password) {
        User user = repo.findByUsername(username).orElse(null);

        if (user == null) return null;
        if (!user.getPassword().equals(password)) return null;

        user.setLastLogin(LocalDateTime.now());
        repo.save(user);

        return user;
    }

    // Self Register (Customer)
    public User register(User user) {
        user.setRole("CUSTOMER");
        return repo.save(user);
    }

    // Admin adds customer manually
    public User addCustomerByAdmin(User user) {
        user.setRole("CUSTOMER");
        return repo.save(user);
    }

    // Admin update any user
    public User updateUser(Long id, User updated) {
        return repo.findById(id).map(u -> {
            u.setUsername(updated.getUsername());
            u.setEmail(updated.getEmail());
            u.setPhone(updated.getPhone());
            u.setPassword(updated.getPassword());
            return repo.save(u);
        }).orElse(null);
    }

    // Delete by Admin
    public boolean deleteUser(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    // View all
    public List<User> getAll() {
        return repo.findAll();
    }

    public Optional<User> getById(Long id) {
        return repo.findById(id);
    }

}
