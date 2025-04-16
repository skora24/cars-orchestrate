package com.kacpers.cars.service;

import com.kacpers.cars.feign.response.LotDetailsResponse;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Service
@RequiredArgsConstructor
public class QueueProcessor<T> {

    private final VehicleCrawlService vehicleCrawlService;

    private final VehicleRepository vehicleRepository;

    private final ModelMapper modelMapper;

    private final AtomicLong counter = new AtomicLong();

    @Async("queueTaskExecutor")
    @Transactional
    protected void processQueue(T item) throws InterruptedException {
        LotDetailsResponse response = vehicleCrawlService.fetchVehicleDetails((Long) item);
        LotVehicle vehicle = modelMapper.map(response.data().lotDetails(), LotVehicle.class);

        LotVehicle persisted = vehicleRepository.findByLotNumber(vehicle.getLotNumber()).get();
        vehicle.setId(persisted.getId());
        vehicle.setCreatedAt(persisted.getCreatedAt());
        vehicleRepository.save(vehicle);

        counter.incrementAndGet();
    }
}
