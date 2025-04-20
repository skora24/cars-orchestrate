-- liquibase formatted sql

-- changeset kacper:create_lot_vehicle_table
CREATE SEQUENCE vehicle_sequence_generator START WITH 10 INCREMENT BY 100;

CREATE TABLE lot_vehicle (
     id BIGINT PRIMARY KEY DEFAULT nextval('vehicle_sequence_generator'),
     created_at TIMESTAMP WITH TIME ZONE,
     updated_at TIMESTAMP WITH TIME ZONE,
     lot_number BIGINT UNIQUE,
     car_fax_report_available BOOLEAN,
     lot_sold BOOLEAN,
     current_bid DOUBLE PRECISION,
     bid_status VARCHAR(255),
     auction_status VARCHAR(255),
     make VARCHAR(255),
     model_short VARCHAR(255),
     model_extended VARCHAR(255),
     model VARCHAR(255),
     model_extra_info VARCHAR(255),
     year INTEGER,
     vin VARCHAR(255),
     estimated_retail_price DOUBLE PRECISION,
     estimated_repair_price DOUBLE PRECISION,
     odometer DOUBLE PRECISION,
     actual_odometer VARCHAR(255),
     engine VARCHAR(255),
     cylinder VARCHAR(255),
     display_name VARCHAR(255),
     auction_house_location VARCHAR(255),
     currency VARCHAR(255),
     sale_date TIMESTAMP WITH TIME ZONE,
     auction_line_number INTEGER,
     title1 VARCHAR(255),
     title2 VARCHAR(255),
     title3 VARCHAR(255),
     title4 VARCHAR(255),
     title5 VARCHAR(255),
     main_damage VARCHAR(255),
     thumbnail_photo_url VARCHAR(255),
     country_location VARCHAR(255),
     city_location VARCHAR(255),
     state_location VARCHAR(255),
     transmission VARCHAR(255),
     damages VARCHAR(255),
     run_status VARCHAR(255),
     paint_color VARCHAR(255),
     interior_color VARCHAR(255),
     fuel VARCHAR(255),
     keys_available VARCHAR(255),
     drive_type VARCHAR(255),
     seller_name VARCHAR(255),
     vehicle_type VARCHAR(255),
     seller_company_name VARCHAR(255),
     auction_provider VARCHAR(255)
);

-- rollback DROP TABLE lot_vehicle; DROP SEQUENCE vehicle_sequence_generator;

-- changeset kacper:create_lot_image_table
CREATE SEQUENCE image_sequence_generator START WITH 10 INCREMENT BY 100;

CREATE TABLE lot_image (
     id BIGINT PRIMARY KEY DEFAULT nextval('image_sequence_generator'),
     created_at TIMESTAMP WITH TIME ZONE,
     updated_at TIMESTAMP WITH TIME ZONE,
     lot_vehicle_id BIGINT,
     url VARCHAR(255),
     high_res_url VARCHAR(255),
     thumbnail_url VARCHAR(255),
     sequence_number INTEGER,
     FOREIGN KEY (lot_vehicle_id) REFERENCES lot_vehicle(id)
);

-- rollback DROP TABLE lot_image; DROP SEQUENCE image_sequence_generator;