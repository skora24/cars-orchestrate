package com.kacpers.cars.scheduler;

import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.feign.response.LotSearchResponse;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class FetchVehicles {

    private final VehicleCrawlService vehicleCrawlService;

    private final ModelMapper modelMapper;

    private final VehicleService vehicleService;

    private final DetailsQueueService queueService;

    private Integer currentPage = 0;

    private final List<Integer> numberOfFoundOnPage = new LinkedList<>();

    private static final Logger log = LoggerFactory.getLogger(FetchVehicles.class);

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void fetchVehicles() {
        log.info("Fetching vehicles for page {}", currentPage);
        // Request cars from external provider
        Set<LotSearchResponse.LotSearch> searched = vehicleCrawlService
            .fetchVehicles(LotSearchRequest.latest(currentPage))
            .content();

        Set<LotVehicle> vehicles = searched.stream()
            .map(v -> modelMapper.map(v, LotVehicle.class))
            .collect(Collectors.toSet());

        log.info("Fetched {} vehicles for page {}", searched.size(), currentPage);
        vehicleService.bulkUpsertVehicles(vehicles.stream().toList());

        vehicles.stream()
            .map(LotVehicle::getLotNumber)
            .forEach(queueService::enqueueMoveToEnd);

        numberOfFoundOnPage.addLast(searched.size());
        changePage();
    }

    private void changePage() {
        if (numberOfFoundOnPage.size() > 10) {
            numberOfFoundOnPage.removeFirst();
        }

        int sumOfTen = numberOfFoundOnPage
            .stream()
            .mapToInt(Integer::intValue)
            .sum();

        if (sumOfTen == 0) {
            currentPage = 0;
            numberOfFoundOnPage.clear();
        }
        currentPage++;
    }
}
