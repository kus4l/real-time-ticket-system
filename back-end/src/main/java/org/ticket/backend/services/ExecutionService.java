package org.ticket.backend.services;

import org.slf4j.Logger; // Importing Logger to log events
import org.springframework.stereotype.Service; // Importing Service annotation for Spring service layer
import org.ticket.backend.logger.TicketLogger; // Importing custom TicketLogger for logging
import org.ticket.backend.models.Configuration; // Importing Configuration model
import org.ticket.backend.repositories.ConfigRepository; // Importing repository to fetch configuration from DB
import org.ticket.backend.util.Customer; // Importing Customer class from utility package
import org.ticket.backend.util.TicketPool; // Importing TicketPool class to manage tickets
import org.ticket.backend.util.Vendor; // Importing Vendor class to simulate vendors

import java.util.Optional; // Importing Optional to handle possible null values

@Service // Marks this class as a Spring service component
public class ExecutionService {

    private static final Logger logger = TicketLogger.getLogger(); // Logger to log events during system execution
    private final ConfigRepository configRepository; // ConfigRepository to fetch configuration from the database
    private TicketPool ticketPool; // The ticket pool that will be managed by the ticketing system
    private Boolean isRunning; // Boolean flag to track if the ticketing system is running

    // Constructor that initializes the config repository and sets the system state to not running
    public ExecutionService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        this.isRunning = false; // Initially, the system is not running
    }

    //Sets the execution state of the ticketing system.
    public void setExecution(Boolean execution) {
        if (execution) {
            if (isRunning) { // If the system is already running, log a warning and return
                logger.warn("Ticketing system is already running!");
                return;
            }
            Optional<Configuration> configuration = configRepository.findById(1); // Fetch configuration from DB
            startTicketSystem(configuration); // Start the system if configuration is valid
        } else {
            stopTicketSystem(); // Stop the system if execution is false
        }
    }

    //Starts the ticketing system with the given configuration.
    private void startTicketSystem(Optional<Configuration> configuration) {
        if (configuration.isEmpty()) { // If configuration is not found, log an error
            logger.error("Configuration not found! Unable to start the ticketing system.");
            return;
        }

        Configuration config = configuration.get(); // Get the configuration object
        logger.info("Starting ticketing system...");
        ticketPool = new TicketPool(config.getMaxTicketCapacity()); // Initialize the ticket pool with max capacity
        isRunning = true; // Set the system state to running

        logger.info("Ticketing system started successfully.");

        // Create and start vendor threads for adding tickets to the pool
        Thread vendorThread1 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-1"));
        Thread vendorThread2 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-2"));
        Thread vendorThread3 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-3"));
        Thread vendorThread4 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-4"));

        // Start the vendor threads
        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();
        vendorThread4.start();

        // Wait for vendors to add tickets (adjust delay as needed)
        try {
            Thread.sleep(config.getTicketReleaseRate() * 2L); // Adjust this delay as needed to ensure tickets are available
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interruption during sleep
        }

        // Create and start customer threads for purchasing tickets from the pool
        Thread customerThread1 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-1"));
        Thread customerThread2 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-2"));
        Thread customerThread3 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-3"));
        Thread customerThread4 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-4"));

        // Start the customer threads
        customerThread1.start();
        customerThread2.start();
        customerThread3.start();
        customerThread4.start();
    }

    //Stops the ticketing system.
    private void stopTicketSystem() {
        if (!isRunning) { // If the system is not running, log a warning and return
            logger.warn("Ticketing system is not running!");
            return;
        }

        logger.info("Stopping ticketing system...");
        ticketPool = null; // Clear the ticket pool
        isRunning = false; // Set the system state to not running

        // Additional cleanup logic if needed
        logger.info("Ticketing system stopped successfully.");
    }
}