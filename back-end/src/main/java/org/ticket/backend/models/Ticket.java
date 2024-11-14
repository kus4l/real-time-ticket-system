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
@AllArgsConstructor
@NoArgsConstructor
public class Ticket
{
    @Id
    private int ticketID;
    private int ticketNumber;
    private String ticketType;
    private String ticketDate;
    private String ticketTime;
    private String ticketPrice;
}
