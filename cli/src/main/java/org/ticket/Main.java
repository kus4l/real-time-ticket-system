package org.ticket;

import org.slf4j.Logger;

import java.util.Scanner;

// Main class for the Real-Time Ticketing System application
public class Main {
    // Flag to track whether the ticketing system is running
    private static boolean isRunning = false;
    // Logger instance for logging system messages
    private static final Logger logger = TicketLogger.getLogger();
    // TicketPool instance to manage ticket storage
    private static TicketPool ticketPool;

    public static void main(String[] args) {
        // Initialize the configuration for the ticketing system
        TicketSystemConfig config = new TicketSystemConfig();

        // Load configuration settings from a file or configure via CLI
        config.loadConfiguration();

        // Display system information and user command options
        System.out.println("REAL TIME TICKETING SYSTEM!");
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
        System.out.println("Enter command (start, stop, status, exit, configure): ");

        // Initialize a scanner to read user commands
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            // Read the user's command
            command = scanner.nextLine();

            // Handle different commands based on user input
            switch (command.toLowerCase()) {
                case "start":
                    // Start the ticketing system if it's not already running
                    if (!isRunning) {
                        startTicketSystem(config);
                    } else {
                        logger.info("System is already running.");
                    }
                    break;
                case "stop":
                    // Stop the ticketing system if it's running
                    if (isRunning) {
                        stopTicketSystem();
                    } else {
                        logger.info("System is not running.");
                    }
                    break;
                case "status":
                    // Log the current status of the system
                    logger.info("Current status: " + (isRunning ? "Running" : "Stopped"));
                    break;
                case "configure":
                    // Allow the user to reconfigure the system via CLI
                    config.configureViaCLI();
                    break;
                case "exit":
                    // Stop the system and exit the application
                    stopTicketSystem();
                    logger.info("Exiting system.");
                    return;
                default:
                    // Notify the user of invalid command input
                    System.out.println("Invalid command.");
            }
        }
    }

    // Start the ticketing system with the provided configuration
    private static void startTicketSystem(TicketSystemConfig config) {
        logger.info("Ticket system started.");
        // Initialize the ticket pool with the maximum ticket capacity
        ticketPool = new TicketPool(config.getMaxTicketCapacity());
        isRunning = true;

        // Create vendor threads to release tickets into the pool
        Thread vendorThread1 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-1"));
        Thread vendorThread2 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-2"));
        Thread vendorThread3 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-3"));
        Thread vendorThread4 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-4"));

        // Start vendor threads
        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();
        vendorThread4.start();

        // Wait briefly to allow vendors to populate tickets
        try {
            Thread.sleep(config.getTicketReleaseRate() * 2L); // Adjust delay based on ticket release rate
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Create customer threads to retrieve tickets from the pool
        Thread customerThread1 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-1"));
        Thread customerThread2 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-2"));
        Thread customerThread3 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-3"));
        Thread customerThread4 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-4"));

        // Start customer threads
        customerThread1.start();
        customerThread2.start();
        customerThread3.start();
        customerThread4.start();
    }

    // Stop the ticketing system and release resources
    private static void stopTicketSystem() {
        isRunning = false;
        // Stop ticket pool operations if initialized
        if (ticketPool != null) {
            ticketPool.stop();
        }
        logger.info("Ticket system stopped.");
    }
}