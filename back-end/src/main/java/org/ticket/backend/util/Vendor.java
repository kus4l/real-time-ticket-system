package org.ticket.backend.util;

import org.slf4j.Logger;
import org.ticket.Ticket;
import org.ticket.backend.logger.TicketLogger;

public class Vendor implements Runnable {
    private static final Logger logger = TicketLogger.getLogger(); // Logger for tracking vendor activities
    private final TicketPool ticketPool; // The pool where tickets are stored
    private final int releaseRate; // The rate at which the vendor releases tickets (in milliseconds)
    private int totalTickets; // The total number of tickets the vendor can release
    private int ticketId; // The unique ID of the ticket being generated
    private final String vendorName; // The name of the vendor (e.g., Vendor-1)

    // Constructor to initialize the vendor with the ticket pool, release rate, total tickets, and vendor name
    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets, String vendorName) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
        this.vendorName = vendorName;
    }

    // The run method that defines the vendor's behavior in the system
    @Override
    public void run() {
        while (true) {
            try {
                Ticket ticket = new Ticket(); // Create a new ticket instance
                ticketId = ticketPool.generateTicketId(); // Generate a unique ticket ID from the ticket pool

                if (ticketId > totalTickets) {
                    break; // Stop if the vendor has reached the total ticket limit
                }

                ticket.setTicketId(ticketId); // Set the ticket ID for the new ticket
                ticketPool.addTickets(ticket, vendorName); // Add the generated ticket to the ticket pool

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption, and stop the thread if necessary
                return; // Exit the loop and terminate the vendor thread
            }

            try {
                Thread.sleep(releaseRate); // Simulate the rate at which tickets are released by the vendor
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption during sleep
                return; // Exit the loop and terminate the vendor thread
            }
        }
        logger.info(vendorName + " has reached its limit and is closing."); // Log the closure of the vendor
    }
}
