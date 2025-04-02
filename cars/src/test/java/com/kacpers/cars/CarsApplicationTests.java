package com.kacpers.cars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kacpers.cars.service.VehicleCrawlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CarsApplicationTests {

    @Autowired
    private VehicleCrawlService vehicleCrawlService;

    @Test
    void contextLoads() throws JsonProcessingException {
        System.out.println(vehicleCrawlService.fetchVehicles());
    }

}
