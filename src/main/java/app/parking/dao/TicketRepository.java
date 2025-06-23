package app.parking.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.parking.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

	@Query("SELECT t FROM Ticket t WHERE name = ?1 "
			+ "AND t.vehicle.vehicleNumber =?2 "
			+ "AND t.exitTime IS NULL")
	Ticket findByNameAndVehicleNumberAndExitTimeIsNull(String name, String vehicleNumber);
}
