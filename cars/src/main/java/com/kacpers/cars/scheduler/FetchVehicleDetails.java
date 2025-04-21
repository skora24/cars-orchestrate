package com.kacpers.cars.scheduler;

import com.kacpers.cars.feign.response.LotDetailsResponse;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.service.DetailsQueueService;
import com.kacpers.cars.service.VehicleCrawlService;
import com.kacpers.cars.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class FetchVehicleDetails {

    private final VehicleCrawlService vehicleCrawlService;

    private final ModelMapper modelMapper;

    private final VehicleService vehicleService;

    private final DetailsQueueService queueService;

    private static final Logger log = LoggerFactory.getLogger(FetchVehicleDetails.class);

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void fetchVehicleDetails() {
        Long lotNumber = queueService.poll();
        if (lotNumber == null) {
            return;
        }

        log.info("Fetching vehicle details for lot number {}", lotNumber);
        // Request car details from external provider
        LotDetailsResponse searched = vehicleCrawlService
            .fetchVehicleDetails(lotNumber);

        LotVehicle vehicle = modelMapper.map(searched.data().lotDetails(), LotVehicle.class);

        log.info("Fetched vehicle details for lot number {}", lotNumber);
        vehicleService.bulkUpsertVehicles(List.of(vehicle));

        queueService.enqueue(lotNumber);
    }

}
