package com.ventaTickets.Recintos.Exception;

public class AsientoYaExisteException extends RuntimeException {
    public AsientoYaExisteException(Integer asiento) {
        super("Ya existe un recinto con el asiento número: " + asiento);
    }
}
