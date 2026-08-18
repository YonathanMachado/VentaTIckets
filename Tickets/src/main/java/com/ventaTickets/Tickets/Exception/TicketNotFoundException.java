package com.ventaTickets.Tickets.Exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Integer id) {
        super("Ticket no encontrado con id: " + id);
    }
    public TicketNotFoundException(String codigo) {
        super("Ticket no encontrado con código: " + codigo);
    }
}
