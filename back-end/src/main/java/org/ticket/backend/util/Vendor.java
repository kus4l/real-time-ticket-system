package org.ticket.backend.util;

import org.slf4j.Logger;
import org.ticket.Ticket;
import org.ticket.backend.logger.TicketLogger;

public class Vendor implements Runnable {
    private static final Logger logger = TicketLogger.getLogger();
    private final TicketPool ticketPool;
    private final int releaseRate;
    private int totalTickets;
    private int ticketId;
    private final String vendorName;

    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets, String vendorName) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Ticket ticket = new Ticket();
                ticketId = ticketPool.generateTicketId();

                if (ticketId > totalTickets) {
                    break;
                }

                ticket.setTicketId(ticketId);
                ticketPool.addTickets(ticket, vendorName);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        logger.info(vendorName + " has reached its limit and is closing.");
    }
}