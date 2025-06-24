package app.parking.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.parking.entity.ParkingSpace;
import app.parking.entity.ParkingSpacePK;
import app.parking.entity.VehicleCategory;
import jakarta.validation.constraints.NotNull;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, ParkingSpacePK> {

	@Modifying
	@Query("DELETE FROM ParkingSpace ps WHERE ps.parkingSpacePK.name = ?1 "
			+ "AND ps.parkingSpacePK.floorNumber = ?2 "
			+ "AND ps.parkingSpacePK.vehicleCategory = ?3 "
			+ "AND ps.parkingSpacePK.spaceNumber = ?4")
	Integer deleteByNameAndFloorNumberAndVehicleCategoryAndSpaceNumber(String name, Integer floorNumber,
			VehicleCategory vehicleCategory, Integer spaceNumber);

	@Query("SELECT ps FROM ParkingSpace ps WHERE ps.parkingSpacePK.name = ?1 "
			+ "AND ps.parkingSpacePK.vehicleCategory = ?2 "
			+ "AND ps.isOccupied = ?3")
	List<ParkingSpace> findByNameAndVehicleCategoryAndIsOccupied(@NotNull String name,
			VehicleCategory vehicleCategory, Boolean isOccupied);

	@Query("SELECT ps FROM ParkingSpace ps WHERE ps.parkingSpacePK.name = ?1 "
			+ "AND ps.vehicleNumber = ?2")
	ParkingSpace findByNameAndVehicleNumber(String name, String vehicleNumber);
}
