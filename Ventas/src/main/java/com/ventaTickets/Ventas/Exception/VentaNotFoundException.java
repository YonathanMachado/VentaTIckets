package com.ventaTickets.Ventas.Exception;

public class VentaNotFoundException extends RuntimeException {

    public VentaNotFoundException(Integer id) {
        super("Venta no encontrada con id: " + id);
    }
}
