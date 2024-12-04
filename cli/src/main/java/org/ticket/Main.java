package org.ticket;
import org.slf4j.Logger;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static boolean isRunning = false;
    private static BlockingQueue<Runnable> queue;
    private static final Logger logger = TicketLogger.getLogger();

    public static void main(String[] args) throws InterruptedException {
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

    private static void startTicketSystem(TicketSystemConfig config) throws InterruptedException {

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        Thread vendorThread = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets()));
        Thread vendorThreadTwo = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), config.getTotalTickets()));
        Thread customerThread = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()));
        Thread customerThreadTwo = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()));

        vendorThread.start();
        vendorThreadTwo.start();
        customerThread.start();
        customerThreadTwo.start();
    }

    private static void stopTicketSystem() {
        isRunning = false;
        logger.info("Ticket system stopped.");
    }
}