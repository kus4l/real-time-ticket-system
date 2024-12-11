package org.ticket;

import org.slf4j.Logger;

// Class representing a vendor responsible for adding tickets to the ticket pool
public class Vendor implements Runnable {
    private static final Logger logger = TicketLogger.getLogger(); // Logger instance for logging operations
    private final TicketPool ticketPool; // Shared ticket pool to which tickets are added
    private final int releaseRate; // Rate at which tickets are released (in milliseconds)
    private int totalTickets; // Total number of tickets the vendor is responsible for adding
    private int ticketId; // ID of the ticket being generated
    private final String vendorName; // Name of the vendor for identification

    // Constructor to initialize vendor properties
    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets, String vendorName) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
        this.vendorName = vendorName;
    }

    // Method executed when the vendor thread is started
    @Override
    public void run() {
        while (true) {
            try {
                // Create a new ticket and assign a unique ID
                Ticket ticket = new Ticket();
                ticketId = ticketPool.generateTicketId();

                // Break the loop if the vendor has generated all tickets
                if (ticketId > totalTickets) {
                    break;
                }

                // Set the ticket ID and add the ticket to the pool
                ticket.setTicketId(ticketId);
                ticketPool.addTickets(ticket, vendorName);

            } catch (InterruptedException e) {
                // Handle interruption and exit gracefully
                Thread.currentThread().interrupt();
                return;
            }

            try {
                // Wait for the specified release rate before adding the next ticket
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                // Handle interruption during sleep and exit gracefully
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Log that the vendor has finished its task and is shutting down
        logger.info(vendorName + " has reached its limit and is closing.");
    }
}