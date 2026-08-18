package com.ventaTickets.Devoluciones.DTO;


import lombok.Data;

@Data
public class DevolucionesDTO {
    private Long idTicket;
    private String motivo;
    private Double monto;
}
