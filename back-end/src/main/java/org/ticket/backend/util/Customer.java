package org.ticket.backend.util;

import org.ticket.Ticket; // Importing Ticket class to represent tickets purchased by customers

public class Customer implements Runnable {
    private final TicketPool ticketPool; // Reference to the TicketPool object that manages ticket availability
    private final int retrievalRate; // Rate at which the customer tries to purchase tickets (delay in ms)
    private final String customerName; // The name of the customer (for identification)

    // Constructor to initialize the customer with the ticket pool, retrieval rate, and customer name
    public Customer(TicketPool ticketPool, int retrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.customerName = customerName;
    }

    // The run method simulates the customer purchasing tickets from the pool
    @Override
    public void run() {
        try {
            while (true) {
                // Attempt to purchase a ticket, passing the customer name to the ticket pool for identification
                Ticket ticket = ticketPool.purchaseTicket(customerName);

                // If no tickets are available, exit the loop (system likely has no tickets left)
                if (ticket == null) {
                    break;
                }

                // Simulate the customer taking time to purchase the ticket by sleeping for the retrieval rate duration
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e) {
            // If the thread is interrupted, we handle the interruption properly by resetting the interrupt flag
            Thread.currentThread().interrupt();
        } finally {
            // Print a message when the customer thread closes (no more tickets to purchase)
            System.out.println(customerName + " thread is closing as there are no more tickets to purchase.");
        }
    }
}