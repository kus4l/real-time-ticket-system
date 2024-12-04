package org.ticket;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TicketPool
{

    private final BlockingQueue<Ticket> ticketQueue = new LinkedBlockingQueue<>();
    private int maxTicketCapacity;

    public TicketPool( int maxTicketCapacity )
    {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized void addTickets(Ticket ticket ) throws InterruptedException {
        if( ticket.getTicketId() <= maxTicketCapacity )
        {
            ticketQueue.put(ticket);
            return;
        }
        System.out.println( "Max capacity reached. Can't add tickets." );
    }

    public synchronized void purchaseTicket() throws InterruptedException {
        if( true )
        {
            ticketQueue.take();
            return;
        }
        System.out.println( "No tickets available." );
    }

//    public synchronized int getTickets()
//    {
//        return tickets;
//    }
}