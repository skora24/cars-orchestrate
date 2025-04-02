package com.kacpers.cars.scheduler;

import com.kacpers.cars.repository.VehicleRepository;
import com.kacpers.cars.service.VehicleCrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FetchVehicles {

    private final VehicleCrawlService vehicleCrawlService;

    private final VehicleRepository vehicleRepository;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void fetchVehicles() {
        System.out.println("Fetching vehicles");
        vehicleCrawlService
            .fetchVehicles()
            .forEach(v -> {
                if (vehicleRepository.findByLotNumber(v.getLn()).isEmpty()) {
                    vehicleRepository.save(v);
                    System.out.println("Saved: " + v.getLn());
                }
            });
    }
}
