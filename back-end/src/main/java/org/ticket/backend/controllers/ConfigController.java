package org.ticket.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ticket.backend.models.Configuration;
import org.ticket.backend.services.ConfigService;

@RestController
@RequestMapping("/config") // Base URL for all config-related operations
public class ConfigController {

    @Autowired
    private ConfigService configService;

    // GET method to provide application info
    @GetMapping("/get")
    public String getConfigs() {
        return "The application is up...";
    }

    // PUT method to add a new config
    @PostMapping("/add")
    public String addConfig(@RequestBody Configuration configuration) {
        try {
            configService.addConfig(configuration);
            return "Config added successfully!";
        } catch (Exception e) {
            return "Error adding config: " + e.getMessage();
        }
    }

    // POST method to update an existing config
    @PutMapping("/edit/{configId}")
    public String editConfig(@PathVariable Long configId, @RequestBody Configuration configuration) {
        try {
            boolean isUpdated = configService.editConfig(configId, configuration);
            if (isUpdated) {
                return "Config updated successfully!";
            } else {
                return "Config not found!";
            }
        } catch (Exception e) {
            return "Error updating config: " + e.getMessage();
        }
    }

    // DELETE method to delete a config
    @DeleteMapping("/delete/{configId}")
    public String deleteConfig(@PathVariable Long configId) {
        try {
            boolean isDeleted = configService.deleteConfig(configId);
            if (isDeleted) {
                return "Config deleted successfully!";
            } else {
                return "Config not found!";
            }
        } catch (Exception e) {
            return "Error deleting config: " + e.getMessage();
        }
    }
}
