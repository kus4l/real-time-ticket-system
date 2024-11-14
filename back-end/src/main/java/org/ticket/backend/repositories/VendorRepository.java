package org.ticket.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticket.ticketsystem.models.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer>
{
}
