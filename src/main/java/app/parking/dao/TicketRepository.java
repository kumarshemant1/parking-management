package app.parking.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.parking.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

}
