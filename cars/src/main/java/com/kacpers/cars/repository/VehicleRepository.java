package com.kacpers.cars.repository;

import com.kacpers.cars.model.LotVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface VehicleRepository extends JpaRepository<LotVehicle, Long> {
    default LotVehicle fetchWithImagesById(Long id) {
        return findWithImagesById(id).orElseThrow(() -> new RuntimeException("No image found with id: " + id));
    }

    @Query("""
        SELECT v
        FROM LotVehicle v
        LEFT JOIN FETCH v.images
        """)
    Set<LotVehicle> findWithImages();

    @Query("""
        SELECT v
        FROM LotVehicle v
        LEFT JOIN FETCH v.images
        WHERE v.id = ?1
        """)
    Optional<LotVehicle> findWithImagesById(Long id);

    @Query("""
        SELECT v.lotNumber
        FROM LotVehicle v
        """)
    Set<Long> findAllLotNumbers();

    @Query("""
        SELECT v.lotNumber
        FROM LotVehicle v
        WHERE v.lotNumber in :lotNumbers
        """)
    Set<Long> findExistingLotNumbers(Set<Long> lotNumbers);

    @Query("""
        SELECT v
        FROM LotVehicle v
        WHERE v.lotNumber = :lotNumber
        """)
    Optional<LotVehicle> findByLotNumber(Long lotNumber);
}
