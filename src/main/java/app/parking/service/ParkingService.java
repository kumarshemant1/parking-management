package app.parking.service;

import app.parking.entity.ParkingSpace;
import app.parking.entity.Ticket;
import app.parking.entity.Vehicle;
import jakarta.validation.constraints.NotNull;

public interface ParkingService {

	ParkingSpace addParkingSpace(@NotNull ParkingSpace parkingSpace);
	
	Integer removeParkingSpace(@NotNull ParkingSpace parkingSpace);
	
	ParkingSpace assignParkingSpace(@NotNull String name, @NotNull Vehicle vehicle);
	
	Ticket unassignParkingSpace(@NotNull String name, @NotNull Vehicle vehicle);
}
