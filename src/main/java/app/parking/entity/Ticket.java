package app.parking.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import app.parking.util.Constant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = Constant.PARKING_SCHEMA, name = Constant.TICKET_TABLE)
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ticket_id")
	private UUID ticketId; 
	
	@Column(name = "entry_time", insertable = false, updatable = false)
	private LocalDateTime entryTime;
	
	@Column(name = "exit_time")
	private LocalDateTime exitTime;
	
	// Many tickets may have one vehicle
	@ManyToOne
	@JoinColumn(name = "vehicle_number")
	private Vehicle vehicle;
	
	@Column(name = "bill")
	private float bill;
	
	// name here represents the infrastructure
	@NotNull
	@Column(name = "name")
	private String name;	
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "changed_at")
	private LocalDateTime changedAt;
	
	@Column(name = "changed_by")
	private String changedBy;
}
