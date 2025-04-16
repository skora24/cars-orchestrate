package com.kacpers.cars.service;

import com.kacpers.cars.model.LotVehicle;
import com.kacpers.cars.repository.VehicleRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final JdbcTemplate jdbcTemplate;

    public List<LotVehicle> vehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional
    public void bulkInsertProducts(List<LotVehicle> vehicles) {
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
                ")";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@Nullable PreparedStatement ps, int i) throws SQLException {
                LotVehicle lotVehicle = vehicles.get(i);
                int paramIndex = 0;

                assert ps != null;

                ps.setObject(++paramIndex, OffsetDateTime.now(), Types.TIMESTAMP_WITH_TIMEZONE);
                ps.setObject(++paramIndex, OffsetDateTime.now(), Types.TIMESTAMP_WITH_TIMEZONE);
                ps.setLong(++paramIndex, lotVehicle.getLotNumber());
                ps.setBoolean(++paramIndex, lotVehicle.getCarFaxReportAvailable());
                ps.setBoolean(++paramIndex, lotVehicle.getLotSold());
                ps.setDouble(++paramIndex, lotVehicle.getCurrentBid());
                ps.setString(++paramIndex, lotVehicle.getBidStatus());
                ps.setString(++paramIndex, lotVehicle.getAuctionStatus());
                ps.setString(++paramIndex, lotVehicle.getMake());
                ps.setString(++paramIndex, lotVehicle.getModelShort());
                ps.setString(++paramIndex, lotVehicle.getModelExtended());
                ps.setString(++paramIndex, lotVehicle.getModel());
                ps.setString(++paramIndex, lotVehicle.getModelExtraInfo());
                ps.setInt(++paramIndex, lotVehicle.getYear());
                ps.setString(++paramIndex, lotVehicle.getVin());
                ps.setDouble(++paramIndex, lotVehicle.getEstimatedRetailPrice());
                ps.setDouble(++paramIndex, lotVehicle.getEstimatedRepairPrice());
                ps.setDouble(++paramIndex, lotVehicle.getOdometer());
                ps.setString(++paramIndex, lotVehicle.getActualOdometer());
                ps.setString(++paramIndex, lotVehicle.getEngine());
                ps.setString(++paramIndex, lotVehicle.getCylinder());
                ps.setString(++paramIndex, lotVehicle.getDisplayName());
                ps.setString(++paramIndex, lotVehicle.getAuctionHouseLocation());
                ps.setString(++paramIndex, lotVehicle.getCurrency());
                ps.setObject(++paramIndex, lotVehicle.getSaleDate(), Types.TIMESTAMP_WITH_TIMEZONE);
                ps.setInt(++paramIndex, lotVehicle.getAuctionLineNumber());
                ps.setString(++paramIndex, lotVehicle.getTitle1());
                ps.setString(++paramIndex, lotVehicle.getTitle2());
                ps.setString(++paramIndex, lotVehicle.getTitle3());
                ps.setString(++paramIndex, lotVehicle.getTitle4());
                ps.setString(++paramIndex, lotVehicle.getTitle5());
                ps.setString(++paramIndex, lotVehicle.getMainDamage());
                ps.setString(++paramIndex, lotVehicle.getThumbnailPhotoUrl());
                ps.setString(++paramIndex, lotVehicle.getCountryLocation());
                ps.setString(++paramIndex, lotVehicle.getCityLocation());
                ps.setString(++paramIndex, lotVehicle.getStateLocation());
                ps.setString(++paramIndex, lotVehicle.getTransmission());
                ps.setString(++paramIndex, lotVehicle.getDamages());
                ps.setString(++paramIndex, lotVehicle.getRunStatus());
                ps.setString(++paramIndex, lotVehicle.getPaintColor());
                ps.setString(++paramIndex, lotVehicle.getInteriorColor());
                ps.setString(++paramIndex, lotVehicle.getFuel());
                ps.setString(++paramIndex, lotVehicle.getKeysAvailable());
                ps.setString(++paramIndex, lotVehicle.getDriveType());
                ps.setString(++paramIndex, lotVehicle.getSellerName());
                ps.setString(++paramIndex, lotVehicle.getVehicleType());
                ps.setString(++paramIndex, lotVehicle.getSellerCompanyName());
                ps.setString(++paramIndex, lotVehicle.getAuctionProvider());
            }

            @Override
            public int getBatchSize() {
                return vehicles.size();
            }
        });
    }
}
