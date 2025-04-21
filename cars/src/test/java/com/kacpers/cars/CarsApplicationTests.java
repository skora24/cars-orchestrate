package com.kacpers.cars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacpers.cars.feign.CopartClient;
import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.feign.response.LotSearchResponse;
import com.kacpers.cars.model.LotImage;
import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.ImageRepository;
import com.kacpers.cars.repository.VehicleRepository;
import com.kacpers.cars.service.VehicleCrawlService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarsApplicationTests {

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
    private VehicleCrawlService vehicleCrawlService;

    @Autowired
    private CopartClient copartClient;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void contextLoads() {
        System.out.println(vehicleCrawlService.fetchVehicleDetails(48728465L));
//        LotImage lotImage = LotImage.builder().build();
//        LotVehicle lotVehicle = LotVehicle.builder().lotNumber(1L).build();
//        lotVehicle = vehicleRepository.save(lotVehicle);
//        lotVehicle.setImages(Set.of(lotImage));
//        vehicleRepository.save(lotVehicle);
//        LotVehicle v = vehicleRepository.fetchWithImagesById(10L);
//        System.out.println(v);
//        System.out.println(imageRepository.findAll());
    }

    @Test
    void contextLoads3() throws JsonProcessingException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.copart.ca");
        System.out.println(driver.manage().getCookies().stream().filter(cookie -> cookie.getName().startsWith("incap_ses")).findFirst().get());
        driver.quit();
    }

    @Test
    void contextLoads2() throws JsonProcessingException {
        System.out.println(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1743728400000L), ZoneOffset.of("+02:00")));
    }

}
