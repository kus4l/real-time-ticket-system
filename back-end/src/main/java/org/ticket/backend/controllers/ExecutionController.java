package org.ticket.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ticket.backend.services.ExecutionService;

@RestController
@RequestMapping("/execution")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @PostMapping("/start")
    public String startSimulation() {
        executionService.setExecution(true);
        return "Ticketing system started successfully.";
    }

    @PostMapping("/stop")
    public String stopSimulation() {
        executionService.setExecution(false);
        return "Ticketing system stopped successfully.";
    }
}
