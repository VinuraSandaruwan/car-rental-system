package com.example.booking_service.service;

import com.example.booking_service.data.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository repo;
    private final RestTemplate restTemplate;

    private final String USER_SERVICE_BASE = "http://localhost:8082/api/user/";
    private final String CAR_SERVICE_BASE = "http://localhost:8083/api/cars/";

    public BookingService(BookingRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    // CREATE BOOKING
    public Booking create(Booking booking) {

        // 1. Validate required fields
        if (booking.getUserId() == null || booking.getCarId() == null) {
            throw new RuntimeException("userId and carId are required");
        }
        if (booking.getStartDate() == null || booking.getEndDate() == null) {
            throw new RuntimeException("startDate and endDate are required");
        }

        // 2. Validate user exists
        UserInfo user = restTemplate.getForObject(
                USER_SERVICE_BASE + booking.getUserId(),
                UserInfo.class
        );
        if (user == null) {
            throw new RuntimeException("User not found in user-service");
        }

        // 3. Validate car exists
        CarInfo car = restTemplate.getForObject(
                CAR_SERVICE_BASE + booking.getCarId(),
                CarInfo.class
        );
        if (car == null) {
            throw new RuntimeException("Car not found in car-service");
        }

        // 3.1 Ensure car is available
        if (!"AVAILABLE".equals(car.getStatus())) {
            throw new RuntimeException("Car is currently not available for booking");
        }

        // 4. Check overlapping bookings (Double Booking Prevention)
        List<Booking> conflicts = repo.findOverlappingBookings(
                booking.getCarId(),
                booking.getStartDate(),
                booking.getEndDate()
        );
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("This vehicle is already booked for the selected period.");
        }

        // 5. Calculate dates
        LocalDate start = LocalDate.parse(booking.getStartDate());
        LocalDate end = LocalDate.parse(booking.getEndDate());

        long daysBetween = ChronoUnit.DAYS.between(start, end);

        if (daysBetween <= 0) {
            throw new RuntimeException("endDate must be after startDate");
        }

        booking.setNumberOfDays((int) daysBetween);
        booking.setDailyRate(car.getDailyRates());
        booking.setTotalPrice(daysBetween * car.getDailyRates());

        // 6. Set defaults
        if (booking.getPaymentMethod() == null) {
            booking.setPaymentMethod("CASH");
        }

        booking.setStatus("CONFIRMED");

        // 7. Save booking
        Booking saved = repo.save(booking);

        // 8. Immediately change car status → BOOKED
        String updateStatusUrl = CAR_SERVICE_BASE + booking.getCarId() + "/status?value=BOOKED";

        try {
            restTemplate.put(updateStatusUrl, null);
        } catch (Exception ignored) {}

        return saved;
    }

    // UPDATE (Admin)
    public Booking update(Integer id, Booking newData) {
        Optional<Booking> opt = repo.findById(id);

        if (!opt.isPresent()) {
            return null;
        }

        Booking existing = opt.get();

        existing.setStartDate(newData.getStartDate());
        existing.setEndDate(newData.getEndDate());
        existing.setPaymentMethod(newData.getPaymentMethod());
        existing.setStatus(newData.getStatus());
        existing.setNumberOfDays(newData.getNumberOfDays());
        existing.setDailyRate(newData.getDailyRate());
        existing.setTotalPrice(newData.getTotalPrice());

        return repo.save(existing);
    }

    // CANCEL Booking
    public Booking cancel(Integer id) {

        Optional<Booking> opt = repo.findById(id);

        if (!opt.isPresent()) {
            return null;
        }

        Booking b = opt.get();
        b.setStatus("CANCELLED");

        // Make car AVAILABLE again
        try {
            restTemplate.put(CAR_SERVICE_BASE + b.getCarId() + "/status?value=AVAILABLE", null);
        } catch (Exception ignored) {}

        return repo.save(b);
    }

    // DELETE Booking
    public boolean delete(Integer id) {
        if (!repo.existsById(id)) {
            return false;
        }

        repo.deleteById(id);
        return true;
    }

    // GET All Bookings
    public List<Booking> all() {
        return repo.findAll();
    }

    // GET one by ID
    public Optional<Booking> findById(Integer id) {
        return repo.findById(id);
    }
}
