package org.ticket.backend.util;

import org.ticket.Ticket;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;
    private final String customerName;

    public Customer(TicketPool ticketPool, int retrievalRate, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.customerName = customerName;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Ticket ticket = ticketPool.purchaseTicket(customerName); // Pass customerName
                if (ticket == null) {
                    break; // Exit if no tickets are available and the system is not running
                }
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
        } finally {
            System.out.println(customerName + " thread is closing as there are no more tickets to purchase.");
        }
    }
}