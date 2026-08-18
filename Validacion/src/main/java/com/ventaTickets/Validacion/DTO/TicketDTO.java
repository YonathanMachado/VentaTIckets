package com.ventaTickets.Validacion.DTO;

import lombok.Data;

@Data
public class TicketDTO {
    private Long id;
    private String codigoQr;
    private String estado; 
}
