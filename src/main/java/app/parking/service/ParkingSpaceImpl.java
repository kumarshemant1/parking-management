package app.parking.service;

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
		ticket.setVehicle(vehicle);
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
		
		return null;
	}
	
	private ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
		return parkingSpaceRepository.save(parkingSpace);
	}
	
	private Ticket saveTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
}
