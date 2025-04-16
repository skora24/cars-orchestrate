package com.kacpers.cars.scheduler;

import com.kacpers.cars.feign.CopartFetchInfo;
import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.feign.response.LotSearchResponse;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import com.kacpers.cars.service.QueueService;
import com.kacpers.cars.service.VehicleCrawlService;
import com.kacpers.cars.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FetchVehicles {

    private final VehicleCrawlService vehicleCrawlService;

    private final VehicleRepository vehicleRepository;

    private final CopartFetchInfo copartFetchInfo;

    private final ModelMapper modelMapper;

    private final QueueService<Long> queueService;

    private final VehicleService vehicleService;

    private static final Logger log = LoggerFactory.getLogger(FetchVehicles.class);

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void fetchVehicles() {
        copartFetchInfo.setLastPage(copartFetchInfo.getLastPage() + 1);

        log.info("Fetching vehicles for page {}", copartFetchInfo.getLastPage());

        Set<LotSearchResponse.LotSearch> searched = vehicleCrawlService
            .fetchVehicles(LotSearchRequest.latest(copartFetchInfo.getLastPage()))
            .content();

        Set<LotVehicle> mapped = searched.stream().map(v -> modelMapper.map(v, LotVehicle.class)).collect(Collectors.toSet());

        Set<Long> fetchedLots = mapped.stream().map(LotVehicle::getLotNumber).collect(Collectors.toSet());

        Set<Long> existingLots = vehicleRepository.findExistingLotNumbers(fetchedLots);

        Set<LotVehicle> uniqueVehicles = mapped.stream()
                        .filter(lotVehicle -> !existingLots.contains(lotVehicle.getLotNumber()))
                        .collect(Collectors.toSet());
        log.info("Fetched {} vehicles for page {}", searched.size(), copartFetchInfo.getLastPage());

        vehicleService.bulkInsertProducts(uniqueVehicles.stream().toList());

//        uniqueVehicles.stream().map(LotVehicle::getLotNumber).forEach(queueService::push);
    }
}
