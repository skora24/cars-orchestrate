package com.kacpers.cars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacpers.cars.feign.CopartClient;
import com.kacpers.cars.feign.request.LotSearchRequest;
import com.kacpers.cars.feign.response.LotSearchResponse;
import com.kacpers.cars.service.VehicleCrawlService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;


@SpringBootTest
class CarsApplicationTests {

    @Autowired
    private VehicleCrawlService vehicleCrawlService;

    @Autowired
    private CopartClient copartClient;



    @Test
    void contextLoads() throws JsonProcessingException {
        System.out.println(vehicleCrawlService.fetchVehicleDetails(79608694L));
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
