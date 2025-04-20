package com.kacpers.cars.repository;

import com.kacpers.cars.model.LotImage;
import com.kacpers.cars.model.LotVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ImageRepository extends JpaRepository<LotImage, Long> {

}
