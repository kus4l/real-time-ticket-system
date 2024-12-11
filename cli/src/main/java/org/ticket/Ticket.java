package org.ticket;

// Represents a ticket in the ticketing system
public class Ticket {
    // Unique identifier for the ticket
    private int ticketId;

    // Getter for ticket ID
    public int getTicketId() {
        return ticketId;
    }

    // Setter for ticket ID
    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    // Overrides the toString method to provide a string representation of the ticket
    @Override
    public String toString() {
        return "Ticket ID: " + ticketId;
    }
}