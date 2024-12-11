package org.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Utility class for managing the logger used in the ticketing system
public class TicketLogger {
    // Singleton instance of the Logger, initialized for the "TicketLogger" category
    private static final Logger logger = LoggerFactory.getLogger("TicketLogger");

    // Private constructor to prevent instantiation of this utility class
    private TicketLogger() {
        // Prevent instantiation
    }

    // Provides access to the shared Logger instance
    public static Logger getLogger() {
        return logger;
    }
}