package org.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TicketSystemConfig {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    private static final String CONFIG_FILE = "config.json";

    // Load configuration from config.json
    public void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TicketSystemConfig config = mapper.readValue(new File(CONFIG_FILE), TicketSystemConfig.class);
            this.totalTickets = config.totalTickets;
            this.ticketReleaseRate = config.ticketReleaseRate;
            this.customerRetrievalRate = config.customerRetrievalRate;
            this.maxTicketCapacity = config.maxTicketCapacity;
            System.out.println("Configuration loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    // Save configuration to config.json
    public void saveConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(CONFIG_FILE), this);
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Configure the system via CLI
    public void configureViaCLI() {
        Scanner scanner = new Scanner(System.in);

        this.totalTickets = promptForInt(scanner, "Enter total tickets: ");
        this.ticketReleaseRate = promptForInt(scanner, "Enter ticket release rate (milliseconds): ");
        this.customerRetrievalRate = promptForInt(scanner, "Enter customer retrieval rate (milliseconds): ");
        this.maxTicketCapacity = promptForInt(scanner, "Enter max ticket capacity: ");

        if (validateConfiguration()) {
            saveConfiguration();
        } else {
            System.out.println("Configuration is invalid. Please reconfigure.");
        }
    }

    private int promptForInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) return value;
                else System.out.println("Value must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
    }

    // Validate configuration with early returns
    public boolean validateConfiguration() {
        if (maxTicketCapacity <= 0) {
            System.out.println("Max ticket capacity must be greater than zero.");
            return false;
        }

        if (totalTickets <= 0) {
            System.out.println("Total tickets must be greater than zero.");
            return false;
        }

        if (totalTickets > maxTicketCapacity) {
            System.out.println("Total tickets cannot exceed maximum ticket capacity.");
            return false;
        }

        if (ticketReleaseRate <= 0) {
            System.out.println("Ticket release rate must be a positive number.");
            return false;
        }

        if (customerRetrievalRate <= 0) {
            System.out.println("Customer retrieval rate must be a positive number.");
            return false;
        }

        // If all checks pass
        return true;
    }

    // Getters
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}