package com.kacpers.cars.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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

import java.util.List;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(unique = true)
    private Long ln;

    @ElementCollection
    List<String> siteCodes;

    @Embedded
    DynamicLotDetails dynamicLotDetails;

    private Boolean driveStatus;
    private String vehicleTypeCode;
    private String memberVehicleType;
    private String odometerUOM;
    private Boolean showClaimForm;
    private Double lotPlugAcv;
    private Boolean readyForReplayFlag;
    private Boolean pwlot;
    private Boolean lotYardSameAsKioskYard;
    private Boolean carFaxReportAvailable;
    private Long lotNumberStr;
    private String mkn;
    private String lmg;
    private String lm;
    private String mmod;
    private String mtrim;
    private Integer lcy;
    private String fv;
    private String efv;
    private String evn;
    private Double la;
    private Double rc;
    private String obc;
    private Double orr;

    @ElementCollection
    private List<String> lfd;

    private String ord;
    private String egn;
    private Integer cy;
    private String ld;
    private String yn;
    private String cuc;
    private String tz;
    private Long ad;
    private Long lad;
    private String at;
    private Integer aan;
    private Double hb;
    private Integer ss;
    private String bndc;
    private Double bnp;
    private Boolean sbf;
    private String ts;
    private String stt;
    private String td;
    private String tgc;
    private String tgd;
    private String dd;
    private String tims;

    @ElementCollection
    private List<String> lic;

    private String gr;
    private String dtc;
    private String al;
    private String adt;
    private Integer ynumb;
    private Integer phynumb;
    private Boolean bf;
    private Integer ymin;
    private Double Longitude;
    private Double latitude;
    private String zip;
    private Boolean offFlg;
    private String locCountry;
    private String locCity;
    private String locState;
    private String htsmn;
    private String tmtp;
    private Double myb;
    private String lmc;
    private String lcc;
    private String sdd;
    private String bstl;
    private String lcd;
    private String clr;
    private String ft;
    private String hk;
    private String sn;
    private String drv;
    private String ess;
    private String lsts;
    private Boolean showSeller;
    private Boolean sstpflg;
    private Boolean hcr;
    private String syn;
    private Boolean ifs;
    private Boolean pbf;
    private Double crg;
    private String brand;
    private String gou;
    private Boolean blucar;
    private Integer lstg;
    private String ldu;
    private Boolean pcf;
    private Boolean btcf;
    private Boolean tpfs;
    private Boolean trf;
    private String csc;
    private Boolean mlf;
    private Boolean fcd;
    private Integer slgc;
    private Boolean cfx;
    private Boolean hcfx;
    private Boolean hideLaneItem;
    private Boolean hideGridRow;
    private Boolean isPWlot;
    private Double lspa;

    @Embeddable
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicLotDetails {
        private String errorCode;
        private Integer buyerNumber;
        private String source;
        private Double buyTodayBid;
        private Integer currentBid;
        private Double totalAmountDue;
        private Boolean sealedBid;
        private Boolean firstBid;
        private Boolean hasBid;
        private Boolean sellerReserveMet;
        private Boolean lotSold;
        private String bidStatus;
        private String saleStatus;
        private String counterBidStatus;
        private Boolean startingBidFlag;
        private Boolean buyerHighBidder;
        private Boolean anonymous;
        private Boolean nonSyncedBuyer;
    }
}
