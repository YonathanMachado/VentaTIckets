package com.ventaTickets.Tickets.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class TicketDTO {
    
    @NotNull(message = "El ID del evento es obligatorio")
    private Long idEvento;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precio;
}
