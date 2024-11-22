package org.ticket;
import org.slf4j.Logger;

import java.util.Scanner;

public class Main {
    private static boolean isRunning = false;
    private static ThreadPool threadPool;
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
        int numberOfThreads = 5;
        int maxTasks = 50;
        threadPool = new ThreadPool(numberOfThreads, maxTasks);

        isRunning = true;
        logger.info("Ticket system started.");

        // Start ticket selling (vendors) and purchasing (customers) tasks
        for (int i = 1; i < config.getTotalTickets(); i++) {
            int taskNo = i;
            // Seller Task
            threadPool.execute(() -> {
                String message = Thread.currentThread().getName() + ": Selling Ticket " + taskNo;
                logger.info(message);
            });

            // Customer Task
            threadPool.execute(() -> {
                String message = Thread.currentThread().getName() + ": Purchasing Ticket " + taskNo;
                logger.info(message);
            });

            Thread.sleep(config.getTicketReleaseRate());  // Control the ticket release rate
            Thread.sleep(config.getCustomerRetrievalRate());  // Control the customer retrieval rate
        }
    }

    private static void stopTicketSystem() {
        if (threadPool != null) {
            threadPool.stop();
        }
        isRunning = false;
        logger.info("Ticket system stopped.");
    }
}