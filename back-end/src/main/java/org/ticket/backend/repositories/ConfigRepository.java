package org.ticket.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository to use built-in CRUD operations
import org.springframework.stereotype.Repository; // Importing Repository annotation to indicate this is a repository bean
import org.ticket.backend.models.Configuration; // Importing Configuration model for the repository to operate on

@Repository // Marks this interface as a repository for Spring Data JPA
public interface ConfigRepository extends JpaRepository<Configuration, Integer> {
    // JpaRepository provides methods like save(), findById(), findAll(), delete(), etc., for the Configuration entity
}