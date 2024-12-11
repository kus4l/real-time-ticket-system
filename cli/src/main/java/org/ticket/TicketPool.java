package org.ticket;

import org.slf4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

// Class representing the ticket pool, which manages ticket storage and retrieval
public class TicketPool {
    // Thread-safe queue for storing tickets
    private final BlockingQueue<Ticket> ticketQueue;
    // Atomic counter to ensure unique ticket IDs
    private final AtomicInteger ticketIdGenerator = new AtomicInteger(1);
    // Flag to indicate whether the ticketing system is running
    private volatile boolean isRunning = true;
    // Shared logger instance for logging ticket operations
    private static final Logger logger = TicketLogger.getLogger();

    // Constructor to initialize the ticket pool with a specified capacity
    public TicketPool(int capacity) {
        ticketQueue = new LinkedBlockingQueue<>(capacity);
    }

    // Adds a ticket to the pool, called by vendors
    public synchronized void addTickets(Ticket ticket, String vendorName) throws InterruptedException {
        if (isRunning) {
            ticketQueue.put(ticket); // Adds ticket to the queue
            logger.info(vendorName + " added " + ticket); // Log ticket addition
            notifyAll(); // Notify any waiting threads that a ticket is available
        }
    }

    // Allows a customer to purchase a ticket from the pool
    public synchronized Ticket purchaseTicket(String customerName) throws InterruptedException {
        // Wait if the queue is empty and the system is still running
        while (ticketQueue.isEmpty() && isRunning) {
            wait(); // Block until a ticket becomes available
        }

        // Return null if the system has stopped
        if (!isRunning) {
            return null;
        }

        // Retrieve and remove a ticket from the queue
        Ticket ticket = ticketQueue.take();
        logger.info(customerName + " purchased " + ticket); // Log ticket purchase
        return ticket;
    }

    // Generates a unique ticket ID using an atomic counter
    public int generateTicketId() {
        return ticketIdGenerator.getAndIncrement();
    }

    // Stops the ticket pool, signaling all waiting threads to stop
    public synchronized void stop() {
        isRunning = false;
        notifyAll(); // Notify all waiting threads for graceful shutdown
    }
}