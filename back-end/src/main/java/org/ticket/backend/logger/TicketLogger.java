package org.ticket.backend.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketLogger {
    private static final Logger logger = LoggerFactory.getLogger("TicketLogger");

    private TicketLogger() {
        // Private constructor to prevent instantiation
    }

    public static Logger getLogger() {
        return logger;
    }
}

