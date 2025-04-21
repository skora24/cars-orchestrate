package com.kacpers.cars.service;

import com.kacpers.cars.model.LotImage;
import com.kacpers.cars.repository.ImageRepository;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final JdbcTemplate jdbcTemplate;

    public List<LotImage> findAll() {
        return imageRepository.findAll();
    }

    @Transactional
    public void bulkInsertImages(List<LotImage> images, Long vehicleLotNumber) {
        String sql = "insert into lot_image (" +
            "created_at, updated_at, lot_vehicle_id, url, high_res_url, thumbnail_url, sequence_number" +
            ") values (" +
            "?, ?, ?, ?, ?, ?, ?" +
            ")";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@Nullable PreparedStatement ps, int i) throws SQLException {
                LotImage lotImage = images.get(i);
                int paramIndex = 0;

                assert ps != null;

                if (lotImage.getCreatedAt() != null) {
                    ps.setObject(++paramIndex, lotImage.getCreatedAt(), Types.TIMESTAMP_WITH_TIMEZONE);
                } else {
                    ps.setNull(++paramIndex, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                if (lotImage.getUpdatedAt() != null) {
                    ps.setObject(++paramIndex, lotImage.getUpdatedAt(), Types.TIMESTAMP_WITH_TIMEZONE);
                } else {
                    ps.setNull(++paramIndex, Types.TIMESTAMP_WITH_TIMEZONE);
                }
                if (vehicleLotNumber != null) {
                    ps.setLong(++paramIndex, vehicleLotNumber);
                } else {
                    ps.setNull(++paramIndex, Types.BIGINT);
                }
                if (lotImage.getUrl() != null) {
                    ps.setString(++paramIndex, lotImage.getUrl());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotImage.getHighResUrl() != null) {
                    ps.setString(++paramIndex, lotImage.getHighResUrl());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotImage.getThumbnailUrl() != null) {
                    ps.setString(++paramIndex, lotImage.getThumbnailUrl());
                } else {
                    ps.setNull(++paramIndex, Types.VARCHAR);
                }
                if (lotImage.getSequenceNumber() != null) {
                    ps.setInt(++paramIndex, lotImage.getSequenceNumber());
                } else {
                    ps.setNull(++paramIndex, Types.INTEGER);
                }
            }

            @Override
            public int getBatchSize() {
                return images.size();
            }
        });
    }
}
