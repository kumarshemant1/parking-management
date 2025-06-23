-- shemant.singh, 20/06/2025
GRANT ALL ON SCHEMA parking TO postgres;

CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE IF NOT EXISTS parking.vehicle(
vehicle_number citext PRIMARY KEY,
vehicle_category varchar(50) NOT NULL CHECK (vehicle_category IN ('TWO_WHEELER', 'FOUR_WHEELER'))
);

