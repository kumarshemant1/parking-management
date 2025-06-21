package app.parking.entity;

import app.parking.util.Constant;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = Constant.PARKING_SCHEMA, name = Constant.PARKING_SPACE_TABLE)
public class ParkingSpace {
	
	@EmbeddedId
	private ParkingSpacePK parkingSpacePK;
	
	@Column(name = "is_occupied")
	private boolean isOccupied;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
}
