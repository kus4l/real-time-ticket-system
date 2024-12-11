package org.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Class for managing the configuration of the ticketing system
public class TicketSystemConfig {
    // Configuration properties
    private int totalTickets;           // Total number of tickets to be managed
    private int ticketReleaseRate;      // Rate at which tickets are released (in milliseconds)
    private int customerRetrievalRate;  // Rate at which customers retrieve tickets (in milliseconds)
    private int maxTicketCapacity;      // Maximum ticket capacity for the pool

    // Configuration file name
    private static final String CONFIG_FILE = "config.json";

    // Load configuration from the config.json file
    public void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Deserialize JSON configuration into a TicketSystemConfig object
            TicketSystemConfig config = mapper.readValue(new File(CONFIG_FILE), TicketSystemConfig.class);
            // Set the configuration properties
            this.totalTickets = config.totalTickets;
            this.ticketReleaseRate = config.ticketReleaseRate;
            this.customerRetrievalRate = config.customerRetrievalRate;
            this.maxTicketCapacity = config.maxTicketCapacity;
            System.out.println("Configuration loaded successfully.");
        } catch (IOException e) {
            // Handle exceptions during file reading
            System.out.println("Error loading configuration: " + e.getMessage());
        }
    }

    // Save the current configuration to the config.json file
    public void saveConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Serialize the current configuration into JSON and write to file
            mapper.writeValue(new File(CONFIG_FILE), this);
            System.out.println("Configuration saved successfully.");
        } catch (IOException e) {
            // Handle exceptions during file writing
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Allows configuration via Command Line Interface (CLI)
    public void configureViaCLI() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for configuration values
        this.totalTickets = promptForInt(scanner, "Enter total tickets: ");
        this.ticketReleaseRate = promptForInt(scanner, "Enter ticket release rate (milliseconds): ");
        this.customerRetrievalRate = promptForInt(scanner, "Enter customer retrieval rate (milliseconds): ");
        this.maxTicketCapacity = promptForInt(scanner, "Enter max ticket capacity: ");

        // Validate and save the configuration if valid
        if (validateConfiguration()) {
            saveConfiguration();
        } else {
            System.out.println("Configuration is invalid. Please reconfigure.");
        }
    }

    // Prompts the user for a positive integer input
    private int promptForInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) return value; // Return if valid
                else System.out.println("Value must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
    }

    // Validates the configuration values
    public boolean validateConfiguration() {
        if (maxTicketCapacity <= 0) {
            System.out.println("Max ticket capacity must be greater than zero.");
            return false;
        }

        if (totalTickets <= 0) {
            System.out.println("Total tickets must be greater than zero.");
            return false;
        }

        if (totalTickets < maxTicketCapacity) {
            System.out.println("Maximum tickets cannot exceed total ticket capacity.");
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

    // Getters for configuration properties
    public int getTotalTickets() { return totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
}