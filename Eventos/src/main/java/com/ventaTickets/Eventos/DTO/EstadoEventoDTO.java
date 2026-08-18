package com.ventaTickets.Eventos.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EstadoEventoDTO {
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "^(PROGRAMADO|CANCELADO|FINALIZADO)$", message = "Estado inválido")
    private String estado;
}

