package com.ventaTickets.Ventas.Exception;

public class TicketNoDisponibleException extends RuntimeException {

    public TicketNoDisponibleException(String codigo) {
        super("El ticket con código '" + codigo + "' no está disponible para la venta");
    }
}
