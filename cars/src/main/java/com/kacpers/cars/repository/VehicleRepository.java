package com.kacpers.cars.repository;

import com.kacpers.cars.model.LotVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<LotVehicle, Long> {
    @Query("SELECT v FROM LotVehicle v WHERE v.ln = ?1")
    Optional<LotVehicle> findByLotNumber(Long lotNumber);
}
