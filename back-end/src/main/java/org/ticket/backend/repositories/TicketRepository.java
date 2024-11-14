package org.ticket.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticket.ticketsystem.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer>
{
}
