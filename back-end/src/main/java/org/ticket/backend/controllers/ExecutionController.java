package org.ticket.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ticket.backend.services.ExecutionService;

@RestController // Marks the class as a REST controller to handle HTTP requests
@RequestMapping("/execution") // Base URL for all execution-related operations
public class ExecutionController {

    private final ExecutionService executionService; // Injection of the ExecutionService to control the simulation

    // Constructor to inject the ExecutionService
    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    // POST method to start the ticketing system simulation
    @PostMapping("/start")
    public String startSimulation() {
        executionService.setExecution(true); // Starts the simulation by setting execution to true
        return "Ticketing system started successfully."; // Returns success message
    }

    // POST method to stop the ticketing system simulation
    @PostMapping("/stop")
    public String stopSimulation() {
        executionService.setExecution(false); // Stops the simulation by setting execution to false
        return "Ticketing system stopped successfully."; // Returns success message
    }
}