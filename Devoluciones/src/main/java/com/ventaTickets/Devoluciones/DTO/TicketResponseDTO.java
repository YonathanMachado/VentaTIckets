package com.ventaTickets.Devoluciones.DTO;

import lombok.Data;

@Data
public class TicketResponseDTO {
    private Long id;
    private String estado; // VENDIDO, USADO, CANCELADO
    private Long idEvento;
}
