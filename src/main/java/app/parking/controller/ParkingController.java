package app.parking.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.parking.entity.ParkingSpace;
import app.parking.entity.Ticket;
import app.parking.entity.Vehicle;
import app.parking.service.ParkingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/parking")
public class ParkingController {
	
	private ParkingService parkingService;

	public ParkingController(ParkingService parkingService) {
		this.parkingService = parkingService;
	}
	
	@PostMapping(path = "/add")
	public ParkingSpace addParkingSpace(@RequestBody @Valid ParkingSpace parkingSpace) {
		return parkingService.addParkingSpace(parkingSpace);
	}
	
	@DeleteMapping(path = "/remove")
	public Integer removeParkingSpace(@RequestBody @Valid ParkingSpace parkingSpace) {
		return parkingService.removeParkingSpace(parkingSpace);
	}
	
	@PostMapping(path = "/assign/{name}")
	public ParkingSpace assignParkingSpace(@PathVariable String name, @RequestBody @Valid Vehicle vehicle) {
		return parkingService.assignParkingSpace(name, vehicle);
	}
	
	@PostMapping(path = "/exit/{name}")
	public Ticket unassignParkingSpace(@PathVariable String name, @RequestBody @Valid Vehicle vehicle) {
		return parkingService.unassignParkingSpace(name, vehicle);
	}
}
