package com.kacpers.cars.controller;

import com.kacpers.cars.dto.LotVehicleDto;
import com.kacpers.cars.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehiclesController {

    private final VehicleService vehicleService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<LotVehicleDto>> vehicles(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(
            vehicleService
                .vehicles(pageable)
                .map(x -> modelMapper.map(x, LotVehicleDto.class)),
            HttpStatus.OK
        );
    }

}
