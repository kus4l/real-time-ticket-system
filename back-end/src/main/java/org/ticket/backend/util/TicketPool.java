package org.ticket.backend.util;

import org.slf4j.Logger;
import org.ticket.Ticket;
import org.ticket.TicketLogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final BlockingQueue<Ticket> ticketQueue;
    private final AtomicInteger ticketIdGenerator = new AtomicInteger(1); // Ensures unique ticket IDs
    private volatile boolean isRunning = true;
    private static final Logger logger = TicketLogger.getLogger();

    public TicketPool(int capacity) {
        ticketQueue = new LinkedBlockingQueue<>(capacity);
    }

    public synchronized void addTickets(Ticket ticket, String vendorName) throws InterruptedException {
        if (isRunning) {
            ticketQueue.put(ticket); // Adds ticket to the queue
            logger.info(vendorName + " added " + ticket);
            notifyAll();
        }
    }

    public synchronized Ticket purchaseTicket(String customerName) throws InterruptedException {
        while (ticketQueue.isEmpty() && isRunning) {
            wait(); // Wait until a ticket is available
        }

        if (!isRunning) {
            return null;
        }

        Ticket ticket = ticketQueue.take(); // Retrieves ticket from the queue
        logger.info(customerName + " purchased " + ticket); // Log customer purchase
        return ticket;
    }

    public int generateTicketId() {
        return ticketIdGenerator.getAndIncrement(); // Generates a unique ID
    }

    public synchronized void stop() {
        isRunning = false;
        notifyAll(); // Wake up any waiting threads to allow graceful shutdown
    }
}