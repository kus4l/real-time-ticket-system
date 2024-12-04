package org.ticket;

import org.slf4j.Logger;

import java.util.Scanner;

public class Main {
    private static boolean isRunning = false;
    private static final Logger logger = TicketLogger.getLogger();
    private static TicketPool ticketPool;

    public static void main(String[] args) {
        TicketSystemConfig config = new TicketSystemConfig();

        // Load configuration from file or configure via CLI if needed
        config.loadConfiguration();

        logger.info("Starting with configuration:");
        logger.info("Total Tickets: " + config.getTotalTickets());
        logger.info("Ticket Release Rate: " + config.getTicketReleaseRate());
        logger.info("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        logger.info("Max Ticket Capacity: " + config.getMaxTicketCapacity());

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            logger.info("Enter command (start, stop, status, exit, configure): ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "start":
                    if (!isRunning) {
                        startTicketSystem(config);
                    } else {
                        logger.info("System is already running.");
                    }
                    break;
                case "stop":
                    if (isRunning) {
                        stopTicketSystem();
                    } else {
                        logger.info("System is not running.");
                    }
                    break;
                case "status":
                    logger.info("Current status: " + (isRunning ? "Running" : "Stopped"));
                    break;
                case "configure":
                    config.configureViaCLI();
                    break;
                case "exit":
                    stopTicketSystem();
                    logger.info("Exiting system.");
                    return;
                default:
                    logger.info("Invalid command.");
            }
        }
    }

    private static void startTicketSystem(TicketSystemConfig config) {
        ticketPool = new TicketPool(config.getMaxTicketCapacity());
        isRunning = true;

        Thread vendorThread1 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-1"));
        Thread vendorThread2 = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets(), "Vendor-2"));
        Thread customerThread1 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()));
        Thread customerThread2 = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()));

        vendorThread1.start();
        vendorThread2.start();
        customerThread1.start();
        customerThread2.start();

        logger.info("Ticket system started.");
    }

    private static void stopTicketSystem() {
        isRunning = false;
        if (ticketPool != null) {
            ticketPool.stop();
        }
        logger.info("Ticket system stopped.");
    }
}