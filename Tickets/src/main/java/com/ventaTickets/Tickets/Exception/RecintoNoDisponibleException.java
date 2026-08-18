package com.ventaTickets.Tickets.Exception;

public class RecintoNoDisponibleException extends RuntimeException {
    public RecintoNoDisponibleException(Integer recintoId) {
        super("El recinto con id " + recintoId + " no está disponible o no existe");
    }
}
