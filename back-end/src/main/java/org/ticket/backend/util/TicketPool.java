package org.ticket.backend.util;

import org.slf4j.Logger;
import org.ticket.Ticket; // Importing the Ticket class to represent tickets in the pool
import org.ticket.TicketLogger; // Importing the logger utility for logging

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final BlockingQueue<Ticket> ticketQueue; // A thread-safe queue to hold tickets
    private final AtomicInteger ticketIdGenerator = new AtomicInteger(1); // Ensures unique ticket IDs using atomic counter
    private volatile boolean isRunning = true; // Flag to indicate if the ticket system is running
    private static final Logger logger = TicketLogger.getLogger(); // Logger for tracking operations

    // Constructor to initialize the ticket pool with a specified capacity
    public TicketPool(int capacity) {
        ticketQueue = new LinkedBlockingQueue<>(capacity); // Creating a thread-safe queue with specified capacity
    }

    // Adds a ticket to the pool. This method is synchronized to ensure thread safety.
    public synchronized void addTickets(Ticket ticket, String vendorName) throws InterruptedException {
        if (isRunning) {
            ticketQueue.put(ticket); // Adds ticket to the queue, blocks if the queue is full
            logger.info(vendorName + " added " + ticket); // Log the ticket addition by vendor
            notifyAll(); // Notify waiting threads (customers) that a ticket is available
        }
    }

    // Allows customers to purchase tickets. If no tickets are available, the thread waits.
    public synchronized Ticket purchaseTicket(String customerName) throws InterruptedException {
        while (ticketQueue.isEmpty() && isRunning) {
            wait(); // Wait until a ticket is available or system stops
        }

        if (!isRunning) {
            return null; // If the system is not running, return null to indicate no ticket is available
        }

        Ticket ticket = ticketQueue.take(); // Takes the ticket from the queue
        logger.info(customerName + " purchased " + ticket); // Log the purchase by customer
        return ticket; // Return the purchased ticket
    }

    // Generates a unique ticket ID using an atomic integer for thread safety
    public int generateTicketId() {
        return ticketIdGenerator.getAndIncrement(); // Atomically generates a new ticket ID and increments the counter
    }

    // Stops the ticket pool by setting isRunning to false and notifying all waiting threads
    public synchronized void stop() {
        isRunning = false; // Mark the system as stopped
        notifyAll(); // Wake up any waiting threads to allow graceful shutdown
    }
}
