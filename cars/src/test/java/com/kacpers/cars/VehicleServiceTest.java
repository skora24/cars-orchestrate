package com.kacpers.cars;

import com.kacpers.cars.model.LotImage;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import com.kacpers.cars.service.ImageService;
import com.kacpers.cars.service.VehicleService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // To disable scheduler
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VehicleServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    static void beforeAll() {
        postgresContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void testUpsert() {
        vehicleService.bulkUpsertVehicles(
            List.of(
                LotVehicle
                    .builder()
                    .updatedAt(OffsetDateTime.of(2000, 12, 1, 6, 0, 0 ,0, ZoneOffset.of("+02:00")))
                    .lotNumber(1L)
                    .build(),
                LotVehicle
                    .builder()
                    .updatedAt(OffsetDateTime.of(2000, 12, 1, 6, 0, 0 ,0, ZoneOffset.of("+02:00")))
                    .lotNumber(2L)
                    .build()
            )
        );
        List<LotVehicle> preUpdate = vehicleRepository.findAll();

        vehicleService.bulkUpsertVehicles(
            List.of(
                LotVehicle
                .builder()
                .updatedAt(OffsetDateTime.of(2001, 12, 1, 6, 0, 0 ,0, ZoneOffset.of("+02:00")))
                .lotNumber(1L)
                .build()
            )
        );
        List<LotVehicle> postUpdate = vehicleRepository.findAll();

        LotVehicle preVehicleId1 = preUpdate.stream().filter(v -> v.getLotNumber().equals(1L)).findFirst().orElseThrow();
        LotVehicle postVehicleId1 = postUpdate.stream().filter(v -> v.getLotNumber().equals(1L)).findFirst().orElseThrow();
        Assertions.assertEquals(preUpdate.size(), postUpdate.size());
        Assertions.assertEquals(preVehicleId1.getLotNumber(), postVehicleId1.getLotNumber());
        Assertions.assertEquals(preVehicleId1.getId(), postVehicleId1.getId());
        Assertions.assertEquals(2001, postVehicleId1.getUpdatedAt().getYear());
        Assertions.assertEquals(2000, preVehicleId1.getUpdatedAt().getYear());
    }

    @Test
    void testImagesFetch() {
        vehicleService.bulkUpsertVehicles(
            List.of(
                LotVehicle
                    .builder()
                    .updatedAt(OffsetDateTime.of(2000, 12, 1, 6, 0, 0 ,0, ZoneOffset.of("+02:00")))
                    .lotNumber(1L)
                    .build(),
                LotVehicle
                    .builder()
                    .updatedAt(OffsetDateTime.of(2000, 12, 1, 6, 0, 0 ,0, ZoneOffset.of("+02:00")))
                    .lotNumber(2L)
                    .build()
            )
        );

        imageService.bulkInsertImages(
            List.of(
                LotImage
                    .builder()
                    .url("url")
                    .build()
            ),
            1L
        );

        System.out.println(imageService.findAll());
        System.out.println(vehicleRepository.findWithImagesById(1L));
    }
}
