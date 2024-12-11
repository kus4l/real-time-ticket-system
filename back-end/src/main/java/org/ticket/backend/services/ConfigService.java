package org.ticket.backend.services;

import org.springframework.stereotype.Service; // Importing Service annotation to mark this class as a service
import org.ticket.backend.models.Configuration; // Importing Configuration model to handle config-related operations
import org.ticket.backend.repositories.ConfigRepository; // Importing ConfigRepository to access the database for Configuration

import java.util.Optional; // Importing Optional to handle possible null values from the repository

@Service // Marks this class as a service component for Spring dependency injection
public class ConfigService {

    private final ConfigRepository configRepository; // Injecting ConfigRepository to interact with the database

    // Constructor injection of the ConfigRepository dependency
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    // Method to add a new configuration
    public void addConfig(Configuration configuration) {
        configRepository.save(configuration); // Save the configuration to the database
    }

    // Method to fetch the configuration by its ID
    public Configuration getConfig() {
        // Fetch the configuration with ID 1, and if not found, throw a RuntimeException
        Optional<Configuration> config = configRepository.findById(1);
        return config.orElseThrow(() -> new RuntimeException("Configuration not found")); // Return the found config or throw an exception if not present
    }
}