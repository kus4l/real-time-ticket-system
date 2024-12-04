package org.ticket;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;
    private int totalTickets;

    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        while (totalTickets > 0) {
            totalTickets--;
            try {
                ticketPool.addTickets(new Ticket());
                System.out.println("tickets added");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}