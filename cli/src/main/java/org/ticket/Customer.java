package org.ticket;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ticketPool.purchaseTicket();
            try {
                Thread.sleep(retrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}