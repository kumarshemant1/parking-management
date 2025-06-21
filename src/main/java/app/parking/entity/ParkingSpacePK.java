package app.parking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class ParkingSpacePK {

	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "floor_number")
	private Integer floorNumber;
	
	@NotNull
	@Column(name = "vehicle_category")
	@Enumerated(EnumType.STRING)
	private VehicleCategory vehicleCategory;
	
	@NotNull
	@Column(name = "space_number")
	private Integer spaceNumber;
}
