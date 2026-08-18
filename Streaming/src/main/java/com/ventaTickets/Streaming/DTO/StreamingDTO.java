package com.ventaTickets.Streaming.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class StreamingDTO {
    @NotNull(message = "El ID del evento es obligatorio")
    private Long idEvento;

    @NotBlank(message = "La URL de acceso es obligatoria")
    private String urlAcceso;

    @NotBlank(message = "La plataforma es obligatoria")
    private String plataforma;
}
