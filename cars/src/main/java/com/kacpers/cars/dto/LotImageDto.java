package com.kacpers.cars.dto;

import lombok.Builder;

@Builder
public record LotImageDto(
    String url,
    String highResUrl,
    String thumbnailUrl,
    Integer sequenceNumber
) {
}
