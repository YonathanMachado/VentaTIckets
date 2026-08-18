package com.ventaTickets.Ventas.Exception;

public class VentaDuplicadaException extends RuntimeException {

    public VentaDuplicadaException(String codigoTicket) {
        super("Ya existe una venta registrada para el ticket con código: " + codigoTicket);
    }
}
