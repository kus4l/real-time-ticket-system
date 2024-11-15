package org.ticket;
import java.util.Scanner;

public class Main {
    private static boolean isRunning = false;
    private static ThreadPool threadPool;

    public static void main(String[] args) throws InterruptedException {
        TicketSystemConfig config = new TicketSystemConfig();

        // Load configuration from file or configure via CLI if needed
        config.loadConfiguration();

        System.out.println("Starting with configuration:");
        System.out.println("Total Tickets: " + config.getTotalTickets());
        System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
        System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("Enter command (start, stop, status, exit, configure): ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "start":
                    if (!isRunning) {
                        startTicketSystem(config);
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;
                case "stop":
                    if (isRunning) {
                        stopTicketSystem();
                    } else {
                        System.out.println("System is not running.");
                    }
                    break;
                case "status":
                    System.out.println("Current status: " + (isRunning ? "Running" : "Stopped"));
                    break;
                case "configure":
                    config.configureViaCLI();
                    break;
                case "exit":
                    stopTicketSystem();
                    System.out.println("Exiting system.");
                    return;
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private static void startTicketSystem(TicketSystemConfig config) throws InterruptedException {
        int numberOfThreads = 5;
        int maxTasks = 50;
        threadPool = new ThreadPool(numberOfThreads, maxTasks);

        isRunning = true;
        System.out.println("Ticket system started.");

        // Start ticket selling (vendors) and purchasing (customers) tasks
        for (int i = 1; i < config.getTotalTickets(); i++) {
            int taskNo = i;
            // Seller Task
            threadPool.execute(() -> {
                String message = Thread.currentThread().getName() + ": Selling Ticket " + taskNo;
                System.out.println(message);
            });

            // Customer Task
            threadPool.execute(() -> {
                String message = Thread.currentThread().getName() + ": Purchasing Ticket " + taskNo;
                System.out.println(message);
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
        System.out.println("Ticket system stopped.");
    }
}