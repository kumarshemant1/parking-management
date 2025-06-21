-- shemant.singh, 21/06/2025
-- To generate the UUID values based on the combination of computer’s MAC address, current timestamp, and a random value, you use the uuid_generate_v1() function.
-- If you want to generate a UUID value solely based on random numbers, you can use the uuid_generate_v4() function.

GRANT ALL ON SCHEMA parking TO postgres;

CREATE EXTENSION IF NOT EXISTS citext;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS parking.ticket(
ticket_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
entry_time TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
exit_time TIMESTAMP WITH TIME ZONE,
vehicle_number citext NOT NULL REFERENCES parking.vehicle(vehicle_number),
bill REAL DEFAULT 0.0,
name citext NOT NULL,
created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
created_by citext DEFAULT 'SYSTEM',
changed_at TIMESTAMP WITH TIME ZONE,
changed_by citext DEFAULT 'SYSTEM'
);