package org.ticket.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ticket.ticketsystem.services.TicketService;

@RestController
public class TicketController
{
    @Autowired
    private TicketService ticketService;

    @RequestMapping( value = "info", method = RequestMethod.GET)
    public String info(){
        ticketService.getTicket();
        return "The application is up...";
    }

}
