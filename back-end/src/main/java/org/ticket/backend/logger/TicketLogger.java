package org.ticket.backend.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketLogger {
    private static final Logger logger = LoggerFactory.getLogger("TicketLogger"); // Logger instance for ticket-related logs

    // Private constructor to prevent instantiation of this utility class
    private TicketLogger() {
    }

    // Public static method to retrieve the logger instance
    public static Logger getLogger() {
        return logger; // Returns the logger instance for logging ticket-related events
    }
}