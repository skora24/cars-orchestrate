package com.kacpers.cars.scheduler;

import com.kacpers.cars.feign.response.LotImagesResponse;
import com.kacpers.cars.model.LotImage;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.service.ImageService;
import com.kacpers.cars.service.ImagesQueueService;
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
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("prod")
public class FetchVehicleImages {

    private final VehicleCrawlService vehicleCrawlService;

    private final ModelMapper modelMapper;

    private final ImageService imageService;

    private final ImagesQueueService queueService;

    private static final Logger log = LoggerFactory.getLogger(FetchVehicleImages.class);

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void fetchVehicleDetails() {
        Long lotNumber = queueService.poll();
        if (lotNumber == null) {
            return;
        }

        log.info("Fetching vehicle details for lot number {}", lotNumber);
        // Request car details from external provider
        LotImagesResponse searched = vehicleCrawlService
            .fetchVehicleImages(lotNumber);

        List<LotImage> images = searched.data().imagesList().content()
            .stream()
            .map(i -> modelMapper.map(i, LotImage.class))
            .toList();

        log.info("Fetched vehicle images for lot number {}", lotNumber);
        imageService.bulkInsertImages(images, lotNumber);
    }

}
