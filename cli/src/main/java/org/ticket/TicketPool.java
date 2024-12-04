package org.ticket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final BlockingQueue<Ticket> ticketQueue;
    private final AtomicInteger ticketIdGenerator = new AtomicInteger(1); // Ensures unique ticket IDs
    private volatile boolean isRunning = true;

    public TicketPool(int capacity) {
        ticketQueue = new LinkedBlockingQueue<>(capacity);
    }

    public void addTickets(Ticket ticket, String vendorName) throws InterruptedException {
        if (isRunning) {
            ticketQueue.put(ticket); // Adds ticket to the queue
            System.out.println(vendorName + " added " + ticket);
        }
    }

    public Ticket purchaseTicket() throws InterruptedException {
        if (isRunning) {
            Ticket ticket = ticketQueue.take(); // Retrieves ticket from the queue
            System.out.println("Customer purchased " + ticket);
            return ticket;
        }
        return null;
    }

    public int generateTicketId() {
        return ticketIdGenerator.getAndIncrement(); // Generates a unique ID
    }

    public void stop() {
        isRunning = false;
    }
}