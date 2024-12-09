package org.ticket.backend.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity
public class Configuration
{
    @Id
    private int configurationId = 1;
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public Configuration() {
    }

    public Configuration(int configurationId, int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        this.configurationId = configurationId;
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public int getTotalTickets()
    {
        return totalTickets;
    }

    public void setTotalTickets( int totalTickets )
    {
        this.totalTickets = totalTickets;
    }

    public int getMaxTicketCapacity()
    {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity( int maxTicketCapacity )
    {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTicketReleaseRate()
    {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate( int ticketReleaseRate )
    {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate()
    {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate( int customerRetrievalRate )
    {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void saveConfiguration(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter file = new FileWriter(fileName);
        mapper.writeValue(file, this);
        file.close();
    }

    public static Configuration loadConfiguration(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return mapper.readValue(content, Configuration.class);
    }

    public boolean validate() {
        return totalTickets > 0 && maxTicketCapacity > 0 && ticketReleaseRate > 0 && customerRetrievalRate > 0;
    }
}
