package com.kacpers.cars.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LotVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicleSequenceGenerator")
    @SequenceGenerator(
            name = "vehicleSequenceGenerator",
            allocationSize = 100,
            initialValue = 10,
            sequenceName = "vehicle_sequence_generator"
    )
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Column(unique = true)
    private Long lotNumber;

    private Boolean carFaxReportAvailable;

    private Boolean lotSold;

    private Double currentBid;

    private String bidStatus;

    private String auctionStatus;

    private String make;

    private String modelShort;

    private String modelExtended;

    private String model;

    private String modelExtraInfo;

    private Integer year;

    private String vin;

    private Double estimatedRetailPrice;

    private Double estimatedRepairPrice;

    private Double odometer;

    private String actualOdometer;

    private String engine;

    private String cylinder;

    private String displayName;

    private String auctionHouseLocation;

    private String currency;

    private OffsetDateTime saleDate;

    private Integer auctionLineNumber;

    private String title1;

    private String title2;

    private String title3;

    private String title4;

    private String title5;

    private String mainDamage;

    private String thumbnailPhotoUrl;

    private String countryLocation;

    private String cityLocation;

    private String stateLocation;

    private String transmission;

    private String damages;

    private String runStatus;

    private String paintColor;

    private String interiorColor;

    private String fuel;

    private String keysAvailable;

    private String driveType;

    private String sellerName;

    private String vehicleType;

    private String sellerCompanyName;

    private String auctionProvider;
}
