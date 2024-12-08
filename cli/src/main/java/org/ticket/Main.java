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

        System.out.println("REAL TIME TICKETING SYSTEM!");
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
        System.out.println("Enter command (start, stop, status, exit, configure): ");

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
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
                    System.out.println("Invalid command.");
            }
        }
    }

    private static void startTicketSystem(TicketSystemConfig config) {
        logger.info("Ticket system started.");
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

    }

    private static void stopTicketSystem() {
        isRunning = false;
        if (ticketPool != null) {
            ticketPool.stop();
        }
        logger.info("Ticket system stopped.");
    }
}