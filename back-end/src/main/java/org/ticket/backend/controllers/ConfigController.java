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
    public Configuration getConfigs() {
        try {
            return configService.getConfig();
        } catch (Exception e) {
            return null;
        }
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
}
