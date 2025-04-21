package com.kacpers.cars.service;

import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final JdbcTemplate jdbcTemplate;

    public Page<LotVehicle> vehicles(Pageable pageable) {
        return vehicleRepository.findWithImages(pageable);
    }

    @Transactional
    public void bulkUpsertVehicles(List<LotVehicle> vehicles) {
        String sql = "insert into lot_vehicle (" +
            "created_at, updated_at, lot_number, car_fax_report_available, lot_sold, " +
            "current_bid, bid_status, auction_status, make, model_short, " +
            "model_extended, model, model_extra_info, year, vin, "+
            "estimated_retail_price, estimated_repair_price, odometer, actual_odometer, engine, " +
            "cylinder, display_name, auction_house_location, currency, sale_date, " +
            "auction_line_number, title1, title2, title3, title4, "+
            "title5, main_damage, thumbnail_photo_url, country_location, city_location, " +
            "state_location, transmission, damages, run_status, paint_color, " +
            "interior_color, fuel, keys_available, drive_type, seller_name, " +
            "vehicle_type, seller_company_name, auction_provider" +
            ") values (" +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
            "?, ?, ?, ?, ?, ?, ?, ?, ?" +
            ")" +
            "on conflict (lot_number) do update set " +
            "updated_at = excluded.updated_at, car_fax_report_available = excluded.car_fax_report_available, " +
            "lot_sold = excluded.lot_sold, current_bid = excluded.current_bid, " +
            "bid_status = excluded.bid_status, auction_status = excluded.auction_status, make = excluded.make, " +
            "model_short = excluded.model_short, model_extended = excluded.model_extended, model = excluded.model, " +
            "model_extra_info = excluded.model_extra_info, year = excluded.year, vin = excluded.vin, " +
            "estimated_retail_price = excluded.estimated_retail_price, estimated_repair_price = excluded.estimated_repair_price, " +
            "odometer = excluded.odometer, actual_odometer = excluded.actual_odometer, engine = excluded.engine, " +
            "cylinder = excluded.cylinder, display_name = excluded.display_name, currency = excluded.currency, " +
            "sale_date = excluded.sale_date, auction_line_number = excluded.auction_line_number, " +
            "title1 = excluded.title1, title2 = excluded.title2, title3 = excluded.title3, title4 = excluded.title4, " +
            "title5 = excluded.title5, main_damage = excluded.main_damage, thumbnail_photo_url = excluded.thumbnail_photo_url, " +
            "country_location = excluded.country_location, city_location = excluded.city_location, " +
            "state_location = excluded.state_location, transmission = excluded.transmission, damages = excluded.damages, " +
            "run_status = excluded.run_status, paint_color = excluded.paint_color, interior_color = excluded.interior_color, " +
            "fuel = excluded.fuel, keys_available = excluded.keys_available, drive_type = excluded.drive_type, " +
            "seller_name = excluded.seller_name, vehicle_type = excluded.vehicle_type, " +
            "seller_company_name = excluded.seller_company_name, auction_provider = excluded.auction_provider";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@Nullable PreparedStatement ps, int i) throws SQLException {
                LotVehicle lotVehicle = vehicles.get(i);
                int paramIndex = 0;

                assert ps != null;

                if (lotVehicle.getCreatedAt() != null) {
                    ps.setObject(++paramIndex, lotVehicle.getCreatedAt(), Types.TIMESTAMP_WITH_TIMEZONE);
                } else {
                    ps.setNull(++paramIndex, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                if (lotVehicle.getUpdatedAt() != null) {
                    ps.setObject(++paramIndex, lotVehicle.getUpdatedAt());
                } else {
                    ps.setNull(++paramIndex, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                if (lotVehicle.getLotNumber() != null) {
                    ps.setLong(++paramIndex, lotVehicle.getLotNumber());
                } else {
                    ps.setNull(++paramIndex, Types.BIGINT);
                }
                if (lotVehicle.getCarFaxReportAvailable() != null) {
                    ps.setBoolean(++paramIndex, lotVehicle.getCarFaxReportAvailable());
                } else {
                    ps.setNull(++paramIndex, Types.BOOLEAN);
                }
                if (lotVehicle.getLotSold() != null) {
                    ps.setBoolean(++paramIndex, lotVehicle.getLotSold());
                } else {
                    ps.setNull(++paramIndex, Types.BOOLEAN);
                }
                if (lotVehicle.getCurrentBid() != null) {
                    ps.setDouble(++paramIndex, lotVehicle.getCurrentBid());
                } else {
                    ps.setNull(++paramIndex, Types.DOUBLE);
                }
                if (lotVehicle.getBidStatus() != null) {
                    ps.setString(++paramIndex, lotVehicle.getBidStatus());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getAuctionStatus() != null) {
                    ps.setString(++paramIndex, lotVehicle.getAuctionStatus());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getMake() != null) {
                    ps.setString(++paramIndex, lotVehicle.getMake());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getModelShort() != null) {
                    ps.setString(++paramIndex, lotVehicle.getModelShort());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getModelExtended() != null) {
                    ps.setString(++paramIndex, lotVehicle.getModelExtended());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getModel() != null) {
                    ps.setString(++paramIndex, lotVehicle.getModel());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getModelExtraInfo() != null) {
                    ps.setString(++paramIndex, lotVehicle.getModelExtraInfo());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getYear() != null) {
                    ps.setInt(++paramIndex, lotVehicle.getYear());
                } else {
                    ps.setNull(++paramIndex, Types.INTEGER);
                }
                if (lotVehicle.getVin() != null) {
                    ps.setString(++paramIndex, lotVehicle.getVin());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getEstimatedRetailPrice() != null) {
                    ps.setDouble(++paramIndex, lotVehicle.getEstimatedRetailPrice());
                } else {
                    ps.setNull(++paramIndex, Types.DOUBLE);
                }
                if (lotVehicle.getEstimatedRepairPrice() != null) {
                    ps.setDouble(++paramIndex, lotVehicle.getEstimatedRepairPrice());
                } else {
                    ps.setNull(++paramIndex, Types.DOUBLE);
                }
                if (lotVehicle.getOdometer() != null) {
                    ps.setDouble(++paramIndex, lotVehicle.getOdometer());
                } else {
                    ps.setNull(++paramIndex, Types.DOUBLE);
                }
                if (lotVehicle.getActualOdometer() != null) {
                    ps.setString(++paramIndex, lotVehicle.getActualOdometer());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getEngine() != null) {
                    ps.setString(++paramIndex, lotVehicle.getEngine());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getCylinder() != null) {
                    ps.setString(++paramIndex, lotVehicle.getCylinder());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getDisplayName() != null) {
                    ps.setString(++paramIndex, lotVehicle.getDisplayName());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getAuctionHouseLocation() != null) {
                    ps.setString(++paramIndex, lotVehicle.getAuctionHouseLocation());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getCurrency() != null) {
                    ps.setString(++paramIndex, lotVehicle.getCurrency());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getSaleDate() != null) {
                    ps.setObject(++paramIndex, lotVehicle.getSaleDate(), Types.TIMESTAMP_WITH_TIMEZONE);
                } else {
                    ps.setNull(++paramIndex, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                if (lotVehicle.getAuctionLineNumber() != null) {
                    ps.setInt(++paramIndex, lotVehicle.getAuctionLineNumber());
                } else {
                    ps.setNull(++paramIndex, Types.INTEGER);
                }
                if (lotVehicle.getTitle1() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTitle1());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getTitle2() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTitle2());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getTitle3() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTitle3());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getTitle4() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTitle4());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getTitle5() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTitle5());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getMainDamage() != null) {
                    ps.setString(++paramIndex, lotVehicle.getMainDamage());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getThumbnailPhotoUrl() != null) {
                    ps.setString(++paramIndex, lotVehicle.getThumbnailPhotoUrl());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getCountryLocation() != null) {
                    ps.setString(++paramIndex, lotVehicle.getCountryLocation());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getCityLocation() != null) {
                    ps.setString(++paramIndex, lotVehicle.getCityLocation());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getStateLocation() != null) {
                    ps.setString(++paramIndex, lotVehicle.getStateLocation());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getTransmission() != null) {
                    ps.setString(++paramIndex, lotVehicle.getTransmission());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getDamages() != null) {
                    ps.setString(++paramIndex, lotVehicle.getDamages());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getRunStatus() != null) {
                    ps.setString(++paramIndex, lotVehicle.getRunStatus());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getPaintColor() != null) {
                    ps.setString(++paramIndex, lotVehicle.getPaintColor());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getInteriorColor() != null) {
                    ps.setString(++paramIndex, lotVehicle.getInteriorColor());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getFuel() != null) {
                    ps.setString(++paramIndex, lotVehicle.getFuel());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getKeysAvailable() != null) {
                    ps.setString(++paramIndex, lotVehicle.getKeysAvailable());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getDriveType() != null) {
                    ps.setString(++paramIndex, lotVehicle.getDriveType());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getSellerName() != null) {
                    ps.setString(++paramIndex, lotVehicle.getSellerName());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getVehicleType() != null) {
                    ps.setString(++paramIndex, lotVehicle.getVehicleType());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getSellerCompanyName() != null) {
                    ps.setString(++paramIndex, lotVehicle.getSellerCompanyName());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotVehicle.getAuctionProvider() != null) {
                    ps.setString(++paramIndex, lotVehicle.getAuctionProvider());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
            }

            @Override
            public int getBatchSize() {
                return vehicles.size();
            }
        });
    }
}
