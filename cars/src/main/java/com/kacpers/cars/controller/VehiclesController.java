package com.kacpers.cars.controller;

import com.kacpers.cars.dto.LotVehicleDto;
import com.kacpers.cars.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehiclesController {

    private final VehicleService vehicleService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<LotVehicleDto>> vehicles() {
        return new ResponseEntity<>(
            vehicleService
                .vehicles()
                .stream()
                .map(x -> modelMapper.map(x, LotVehicleDto.class))
                .toList(),
            HttpStatus.OK
        );
    }

}
