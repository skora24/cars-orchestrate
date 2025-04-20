package com.kacpers.cars.converter;

import com.kacpers.cars.feign.response.LotImagesResponse;
import com.kacpers.cars.model.LotImage;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class SingleLotImageToLotVehicleConverter implements Converter<LotImagesResponse.Content, LotImage> {
    @Override
    public LotImage convert(MappingContext<LotImagesResponse.Content, LotImage> mappingContext) {
        LotImagesResponse.Content src = mappingContext.getSource();
        return LotImage
            .builder()
            .createdAt(OffsetDateTime.now())
            .updatedAt(OffsetDateTime.now())
            .url(src.fullUrl())
            .highResUrl(src.highResUrl())
            .thumbnailUrl(src.thumbnailUrl())
            .sequenceNumber(src.imageSeqNumber())
            .build();
    }
}
