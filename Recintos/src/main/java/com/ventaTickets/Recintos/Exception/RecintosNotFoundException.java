package com.ventaTickets.Recintos.Exception;

public class RecintosNotFoundException extends RuntimeException {
    public RecintosNotFoundException(Integer id) {
        super("Recinto no encontrado con id: " + id);
    }
}
