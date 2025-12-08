package com.example.maintenance_service.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance,Long>{

}
