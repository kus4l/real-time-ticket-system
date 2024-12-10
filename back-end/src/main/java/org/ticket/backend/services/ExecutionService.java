package org.ticket.backend.services;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.ticket.backend.logger.TicketLogger;
import org.ticket.backend.models.Configuration;
import org.ticket.backend.repositories.ConfigRepository;
import org.ticket.backend.util.Customer;
import org.ticket.backend.util.TicketPool;
import org.ticket.backend.util.Vendor;

import java.util.Optional;

@Service
public class ExecutionService {

    private static final Logger logger = TicketLogger.getLogger();
    private final ConfigRepository configRepository;
    private TicketPool ticketPool;
    private Boolean isRunning;

    public ExecutionService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
        this.isRunning = false;
    }

    /**
     * Sets the execution state of the ticketing system.
     *
     * @param execution true to start the system, false to stop it
     */
    public void setExecution(Boolean execution) {
        if (execution) {
            if (isRunning) {
                logger.warn("Ticketing system is already running!");
                return;
            }
            Optional<Configuration> configuration = configRepository.findById(1);
            startTicketSystem(configuration);
        } else {
            stopTicketSystem();
        }
    }

    /**
     * Starts the ticketing system with the given configuration.
     *
     * @param configuration the configuration to initialize the ticketing system
     */
    private void startTicketSystem(Optional<Configuration> configuration) {
        if (configuration.isEmpty()) {
            logger.error("Configuration not found! Unable to start the ticketing system.");
            return;
        }

        Configuration config = configuration.get();
        logger.info("Starting ticketing system...");
        ticketPool = new TicketPool(config.getMaxTicketCapacity());
        isRunning = true;

        // Vendor threads
        Thread vendorThread1 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-1"));
        Thread vendorThread2 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-2"));
        Thread vendorThread3 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-3"));
        Thread vendorThread4 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-4"));

        // Start vendor threads
        vendorThread1.start();
        vendorThread2.start();
        vendorThread3.start();
        vendorThread4.start();

        // Wait for vendors to add initial tickets
        try {
            Thread.sleep(config.getTicketReleaseRate() * 2L); // Adjust this delay as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Customer threads
        Thread customerThread1 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-1"));
        Thread customerThread2 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-2"));
        Thread customerThread3 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-3"));
        Thread customerThread4 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate(), "Customer-4"));

        // Start customer threads
        customerThread1.start();
        customerThread2.start();
        customerThread3.start();
        customerThread4.start();

        logger.info("Ticketing system started successfully.");
    }

    /**
     * Stops the ticketing system.
     */
    private void stopTicketSystem() {
        if (!isRunning) {
            logger.warn("Ticketing system is not running!");
            return;
        }

        logger.info("Stopping ticketing system...");
        ticketPool = null; // Clear the ticket pool
        isRunning = false;

        // Additional cleanup logic if needed
        logger.info("Ticketing system stopped successfully.");
    }
}
