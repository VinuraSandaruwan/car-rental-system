package com.example.booking_service.data;

import com.example.booking_service.data.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.carId = :carId AND b.status = 'CONFIRMED' " +
            "AND (b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Booking> findOverlappingBookings(
            @Param("carId") Integer carId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
