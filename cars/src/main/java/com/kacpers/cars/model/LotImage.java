package com.kacpers.cars.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "vehicle")
public class LotImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageSequenceGenerator")
    @SequenceGenerator(
        name = "imageSequenceGenerator",
        allocationSize = 100,
        initialValue = 10,
        sequenceName = "image_sequence_generator"
    )
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_vehicle_id")
    private LotVehicle vehicle;

    private String url;

    private String highResUrl;

    private String thumbnailUrl;

    private Integer sequenceNumber;
}
