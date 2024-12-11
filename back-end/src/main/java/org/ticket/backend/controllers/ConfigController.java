package org.ticket.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticket.backend.models.Configuration;
import org.ticket.backend.services.ConfigService;

@RestController // Marks the class as a REST controller to handle HTTP requests
@RequestMapping("/config") // Base URL for all config-related operations
public class ConfigController {

    @Autowired // Injects the ConfigService into this controller
    private ConfigService configService;

    // GET method to provide application configuration info
    @GetMapping("/get")
    public Configuration getConfigs() {
        try {
            return configService.getConfig(); // Retrieves the configuration using the service
        } catch (Exception e) {
            return null; // Returns null in case of an exception
        }
    }

    // POST method to add a new configuration
    @PostMapping("/add")
    public String addConfig(@RequestBody Configuration configuration) {
        try {
            configService.addConfig(configuration); // Adds the new configuration using the service
            return "Config added successfully!"; // Success message
        } catch (Exception e) {
            return "Error adding config: " + e.getMessage(); // Error message with exception details
        }
    }
}