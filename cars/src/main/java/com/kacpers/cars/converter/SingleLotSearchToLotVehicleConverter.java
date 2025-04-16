package com.kacpers.cars.converter;

import com.kacpers.cars.feign.response.LotSearchResponse;
import com.kacpers.cars.model.LotVehicle;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class SingleLotSearchToLotVehicleConverter implements Converter<LotSearchResponse.LotSearch, LotVehicle> {
    @Override
    public LotVehicle convert(MappingContext<LotSearchResponse.LotSearch, LotVehicle> mappingContext) {
        LotSearchResponse.LotSearch src = mappingContext.getSource();

        return LotVehicle
            .builder()
            .lotNumber(src.ln())
            .carFaxReportAvailable(src.carFaxReportAvailable())
            .lotSold(src.dynamicLotDetails().lotSold())
            .currentBid(src.dynamicLotDetails().currentBid())
            .bidStatus(src.dynamicLotDetails().bidStatus())
            .auctionStatus(src.dynamicLotDetails().saleStatus())
            .make(src.mkn())
            .modelShort(src.lmg())
            .modelExtended(src.lm())
            .model(src.mmod())
            .modelExtraInfo(src.mtrim())
            .year(src.lcy())
            .vin(src.fv())
            .estimatedRetailPrice(src.la())
            .estimatedRepairPrice(src.rc())
            .odometer(src.orr())
            .actualOdometer(src.obc())
            .engine(src.egn())
            .cylinder(src.cy())
            .displayName(src.ld())
            .auctionHouseLocation(src.yn())
            .currency(src.cuc())
            .saleDate(src.ad() != null ? OffsetDateTime.ofInstant(Instant.ofEpochMilli(src.ad()), ZoneOffset.of("+02:00")) : null)
            .auctionLineNumber(src.aan())
            .title1(src.ts())
            .title2(src.stt())
            .title3(src.td())
            .title4(src.tgc())
            .title5(src.tgd())
            .mainDamage(src.dd())
            .thumbnailPhotoUrl(src.tims())
            .countryLocation(src.locCountry())
            .cityLocation(src.locCity())
            .stateLocation(src.locState())
            .transmission(src.tmtp())
            .damages(src.sdd())
            .runStatus(src.lcd())
            .paintColor(src.clr())
            .interiorColor(null)
            .fuel(src.ft())
            .keysAvailable(src.hk())
            .driveType(src.drv())
            .sellerName(null)
            .vehicleType(src.vehicleTypeCode())
            .sellerCompanyName(src.syn())
            .auctionProvider(src.brand())
            .build();
    }
}
