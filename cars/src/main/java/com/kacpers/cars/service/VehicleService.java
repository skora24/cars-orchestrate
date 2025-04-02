package com.kacpers.cars.service;

import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public List<LotVehicle> vehicles() {
        return vehicleRepository.findAll();
    }

}
