package org.ticket.backend.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity // Marks this class as a JPA entity to be mapped to a database table
public class Configuration {

    @Id
    private int configurationId = 1; // Unique identifier for the configuration
    private int totalTickets; // Total number of tickets
    private int maxTicketCapacity; // Maximum capacity of the ticket pool
    private int ticketReleaseRate; // Rate at which tickets are released
    private int customerRetrievalRate; // Rate at which customers retrieve tickets

    // Default constructor
    public Configuration() {
    }

    // Parameterized constructor to initialize configuration properties
    public Configuration(int configurationId, int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.configurationId = configurationId;
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Getter and setter for configurationId
    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    // Getter and setter for totalTickets
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    // Getter and setter for maxTicketCapacity
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // Getter and setter for ticketReleaseRate
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    // Getter and setter for customerRetrievalRate
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
}