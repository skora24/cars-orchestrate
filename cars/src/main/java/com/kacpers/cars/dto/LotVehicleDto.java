package com.kacpers.cars.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record LotVehicleDto(
    Long lotNumber,
    String make,
    String model,
    Integer year,
    Double mileage,
    String fuel,
    String city,
    String transmission,
    Double bid,
    String thumbnailUrl,
    List<LotImageDto> images
) {
}
