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
	@Query("DELETE FROM ParkingSpace ps WHERE ps.parkingSpacePK.name = :name "
			+ "AND ps.parkingSpacePK.floorNumber = :floorNumber "
			+ "AND ps.parkingSpacePK.vehicleCategory = :vehicleCategory "
			+ "AND ps.parkingSpacePK.spaceNumber = :spaceNumber")
	Integer deleteByNameAndFloorNumberAndVehicleCategoryAndSpaceNumber(String name, Integer floorNumber,
			VehicleCategory vehicleCategory, Integer spaceNumber);

	@Query("SELECT ps FROM ParkingSpace ps WHERE ps.parkingSpacePK.name = :name "
			+ "AND ps.parkingSpacePK.vehicleCategory = :vehicleCategory "
			+ "AND ps.isOccupied = :isOccupied")
	List<ParkingSpace> findByNameAndVehicleCategoryAndIsOccupied(@NotNull String name,
			VehicleCategory vehicleCategory, Boolean isOccupied);

}
