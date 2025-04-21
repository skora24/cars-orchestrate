package com.kacpers.cars.converter;

import com.kacpers.cars.dto.LotImageDto;
import com.kacpers.cars.dto.LotVehicleDto;
import com.kacpers.cars.model.LotVehicle;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class LotVehicleToLotVehicleDtoConverter implements Converter<LotVehicle, LotVehicleDto> {
    @Override
    public LotVehicleDto convert(MappingContext<LotVehicle, LotVehicleDto> mappingContext) {
        LotVehicle src = mappingContext.getSource();
        return LotVehicleDto
            .builder()
            .make(src.getMake())
            .model(src.getModel())
            .year(src.getYear())
            .city(src.getCityLocation())
            .bid(src.getCurrentBid())
            .fuel(src.getFuel())
            .transmission(src.getTransmission())
            .mileage(src.getOdometer())
            .lotNumber(src.getLotNumber())
            .images(src.getImages()
                .stream()
                .map(i -> LotImageDto
                    .builder()
                    .url(i.getUrl())
                    .sequenceNumber(i.getSequenceNumber())
                    .thumbnailUrl(i.getThumbnailUrl())
                    .highResUrl(i.getHighResUrl())
                    .build()
                )
                .toList()
            )
            .build();
    }
}
