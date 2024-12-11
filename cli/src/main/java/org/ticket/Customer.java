package org.ticket;

// Customer class representing a customer in the ticketing system, implements Runnable for multi-threading
public class Customer implements Runnable {
    // Reference to the shared TicketPool for ticket management
    private final TicketPool ticketPool;
    // Rate at which the customer retrieves tickets (in milliseconds)
    private final int retrievalRate;
    // Name of the customer for identification
    private final String customerName;

    // Constructor to initialize the Customer with ticket pool, retrieval rate, and name
    public Customer(TicketPool ticketPool, int retrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.customerName = customerName;
    }

    // Overridden run method defining the behavior of the Customer thread
    @Override
    public void run() {
        try {
            while (true) {
                // Attempt to purchase a ticket from the pool
                Ticket ticket = ticketPool.purchaseTicket(customerName); // Pass customerName for logging or tracking
                if (ticket == null) {
                    // Exit the loop if no tickets are available and the system is not running
                    break;
                }
                // Wait for a specified interval before attempting the next ticket purchase
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e) {
            // Restore the interrupt status if the thread is interrupted
            Thread.currentThread().interrupt();
        } finally {
            // Notify that the customer thread is closing due to no more tickets being available
            System.out.println(customerName + " thread is closing as there are no more tickets to purchase.");
        }
    }
}