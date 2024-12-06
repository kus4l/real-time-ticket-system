package org.ticket.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Configuration
{
    @Id
    private int ticketID;
    private int ticketNumber;
    private String ticketType;
    private String ticketDate;
    private String ticketTime;
    private String ticketPrice;


    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Configuration(int ticketID, int ticketNumber, String ticketType, String ticketDate, String ticketTime, String ticketPrice) {
        this.ticketID = ticketID;
        this.ticketNumber = ticketNumber;
        this.ticketType = ticketType;
        this.ticketDate = ticketDate;
        this.ticketTime = ticketTime;
        this.ticketPrice = ticketPrice;
    }
}
