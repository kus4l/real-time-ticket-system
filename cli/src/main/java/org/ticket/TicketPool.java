package org.ticket;

public class TicketPool
{
    private int tickets;
    private final int maxTicketCapacity;

    public TicketPool( int maxTicketCapacity )
    {
        this.tickets = 0;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public synchronized boolean addTickets( int amount )
    {
        if( tickets + amount <= maxTicketCapacity )
        {
            tickets += amount;
            System.out.println( "Added " + amount + " tickets. Current tickets: " + tickets );
            return true;
        }
        System.out.println( "Max capacity reached. Can't add tickets." );
        return false;
    }

    public synchronized boolean purchaseTicket()
    {
        if( tickets > 0 )
        {
            tickets--;
            System.out.println( "Ticket purchased. Current tickets: " + tickets );
            return true;
        }
        System.out.println( "No tickets available." );
        return false;
    }

    public synchronized int getTickets()
    {
        return tickets;
    }
}