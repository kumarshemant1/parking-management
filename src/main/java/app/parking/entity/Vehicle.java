package app.parking.entity;

import app.parking.util.Constant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = Constant.PARKING_SCHEMA, name = Constant.VEHICLE_TABLE)
public class Vehicle {

	@Id
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	@NotNull
	@Column(name = "vehicle_category")
	@Enumerated(EnumType.STRING)
	private VehicleCategory vehicleCategory;
}
