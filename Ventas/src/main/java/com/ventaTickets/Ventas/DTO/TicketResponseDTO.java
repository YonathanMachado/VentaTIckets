package com.ventaTickets.Ventas.DTO;

import lombok.Data;


@Data
public class TicketResponseDTO {
    private Long id;
    private String codigoQr;
    private String estado;
}