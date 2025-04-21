package com.kacpers.cars.repository;

import com.kacpers.cars.model.LotVehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<LotVehicle, Long> {
    default LotVehicle fetchWithImagesById(Long id) {
        return findWithImagesById(id).orElseThrow(() -> new RuntimeException("No image found with id: " + id));
    }

    @Query("""
        SELECT v
        FROM LotVehicle v
        LEFT JOIN FETCH v.images
        """)
    Page<LotVehicle> findWithImages(Pageable pageable);

    @Query("""
        SELECT v
        FROM LotVehicle v
        LEFT JOIN FETCH v.images
        WHERE v.id = ?1
        """)
    Optional<LotVehicle> findWithImagesById(Long id);

}
