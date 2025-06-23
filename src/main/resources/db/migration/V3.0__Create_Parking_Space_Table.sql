-- shemant.singh, 21/06/2025

GRANT ALL ON SCHEMA parking TO postgres;

CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE IF NOT EXISTS parking.parking_space(
name citext NOT NULL,
floor_number integer NOT NULL,
vehicle_category varchar(50) NOT NULL CHECK (vehicle_category IN ('TWO_WHEELER', 'FOUR_WHEELER')),
space_number integer NOT NULL,
is_occupied boolean DEFAULT false,
vehicle_number citext,
CONSTRAINT "primary_key_name_floor_category_space" PRIMARY KEY (name, floor_number, vehicle_category, space_number),
CONSTRAINT "unique_key_floor_space" UNIQUE (floor_number, space_number)
);