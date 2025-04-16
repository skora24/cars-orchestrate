package com.kacpers.cars.runner;

import com.kacpers.cars.repository.VehicleRepository;
import com.kacpers.cars.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class QueueRunner implements ApplicationRunner {

    private final QueueService<Long> queueService;

    private final VehicleRepository vehicleRepository;

    @Override
    public void run(ApplicationArguments args) {
        Set<Long> lotNumbers = vehicleRepository.findAllLotNumbers();
        queueService.startWatching(lotNumbers);
    }
}
