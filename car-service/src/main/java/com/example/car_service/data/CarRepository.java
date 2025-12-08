package com.example.car_service.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);


}
