package com.kacpers.cars.converter;

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
                .lotNumber(src.getLn())
                .bid(src.getDynamicLotDetails().getCurrentBid())
                .city(src.getYn())
                .fuel(src.getFt())
                .make(src.getLmc())
                .model(src.getLmg())
//                .mileage(src.getHb())
                .year(src.getLcy())
                .transmission(src.getTmtp())
                .thumbnailUrl(src.getTims())
                .build();
    }
}
