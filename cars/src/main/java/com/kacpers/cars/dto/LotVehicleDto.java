package com.kacpers.cars.dto;

import lombok.Builder;

@Builder
public record LotVehicleDto(
        Long lotNumber,
        String make,
        String model,
        Integer year,
        Integer mileage,
        String fuel,
        String city,
        String transmission,
        Integer bid,
        String thumbnailUrl
) {
}
