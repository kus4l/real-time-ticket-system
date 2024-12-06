package org.ticket.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ticket.backend.models.Configuration;

@Repository
public interface ConfigRepository extends JpaRepository<Configuration, Integer>
{
}
