package org.ticket;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ticketPool.addTickets(1);
            try {
                Thread.sleep(releaseRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}