package app.parking.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import app.parking.dao.ParkingSpaceRepository;
import app.parking.dao.TicketRepository;
import app.parking.dao.VehicleRepository;
import app.parking.entity.ParkingSpace;
import app.parking.entity.Ticket;
import app.parking.entity.Vehicle;
import app.parking.entity.VehicleCategory;
import app.parking.exception.NoVacantSpaceException;
import app.parking.exception.VehicleException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Service
public class ParkingSpaceImpl implements ParkingService {
	
	private ParkingSpaceRepository parkingSpaceRepository;
	private TicketRepository ticketRepository;
	private VehicleRepository vehicleRepository;
	
	public ParkingSpaceImpl(ParkingSpaceRepository parkingSpaceRepository, TicketRepository ticketRepository, VehicleRepository vehicleRepository) {
		this.parkingSpaceRepository = parkingSpaceRepository;
		this.ticketRepository = ticketRepository;
		this.vehicleRepository = vehicleRepository;
	}

	@Override
	public ParkingSpace addParkingSpace(@NotNull ParkingSpace parkingSpace) {
		return saveParkingSpace(parkingSpace);
	}

	@Override
	public Integer removeParkingSpace(@NotNull ParkingSpace parkingSpace) {
		String name = parkingSpace.getParkingSpacePK().getName();
		Integer floorNumber = parkingSpace.getParkingSpacePK().getFloorNumber();
		VehicleCategory vehicleCategory = parkingSpace.getParkingSpacePK().getVehicleCategory();
		Integer spaceNumber = parkingSpace.getParkingSpacePK().getSpaceNumber();
		
		// TODO if a particular parking space is occupied by a vehicle then we cant remove the parking unless the vehicle departs 
		
		return parkingSpaceRepository.deleteByNameAndFloorNumberAndVehicleCategoryAndSpaceNumber(name, floorNumber, vehicleCategory, spaceNumber);
	}

	@Override
	@Transactional
	public ParkingSpace assignParkingSpace(@NotNull String name, @NotNull Vehicle vehicle) {
		VehicleCategory vehicleCategory = vehicle.getVehicleCategory();
		ParkingSpace parkingSpace = checkVacantParkingSpace(name, vehicleCategory);
		if(parkingSpace == null) {
			throw new NoVacantSpaceException("No vacant parking space available for " + vehicleCategory + " at the moment, kindly wait");
		}
		parkingSpace.setOccupied(Boolean.TRUE);
		parkingSpace.setVehicleNumber(vehicle.getVehicleNumber());
		saveParkingSpace(parkingSpace);
		
		Ticket ticket = new Ticket();
		ticket.setEntryTime(LocalDateTime.now());
		ticket.setName(name);
		Vehicle existingVehicle = vehicleRepository.findById(vehicle.getVehicleNumber()).orElseGet(() -> vehicleRepository.save(vehicle));
		ticket.setVehicle(existingVehicle);
		saveTicket(ticket);
		return parkingSpace;
	}
	
	private ParkingSpace checkVacantParkingSpace(@NotNull String name, @NotNull VehicleCategory vehicleCategory) {
		ParkingSpace vacantParkingSpace = null;
		List<ParkingSpace> parkingSpaces = parkingSpaceRepository.findByNameAndVehicleCategoryAndIsOccupied(name, vehicleCategory, Boolean.FALSE);
		if(!parkingSpaces.isEmpty()) {
			vacantParkingSpace = parkingSpaces.get(0);
		}
		return vacantParkingSpace;
	}

	@Override
	public Ticket unassignParkingSpace(@NotNull String name, @NotNull Vehicle vehicle) {
		String vehicleNumber = vehicle.getVehicleNumber();
		Ticket ticket = ticketRepository.findByNameAndVehicleNumberAndExitTimeIsNull(name, vehicleNumber);
		if(ticket == null) {
			throw new VehicleException("No such vehicle is parked at the parking space");
		}
		ticket.setExitTime(LocalDateTime.now());
		Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
		long minutes = duration.toMinutes();
		ticket.setBill(minutes*0.33f);
		saveTicket(ticket);
		ParkingSpace parkingSpace = parkingSpaceRepository.findByNameAndVehicleNumber(name, vehicle.getVehicleNumber());
		parkingSpace.setOccupied(Boolean.FALSE);
		parkingSpace.setVehicleNumber(null);
		saveParkingSpace(parkingSpace);
		
		return ticket;
	}
	
	private ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
		return parkingSpaceRepository.save(parkingSpace);
	}
	
	private Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}
