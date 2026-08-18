package com.ventaTickets.Eventos.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventoDTO {

    @NotBlank(message = "El nombre del evento es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La fecha del evento es obligatoria")
    @Future(message = "La fecha del evento debe ser en el futuro")
    private LocalDateTime fechaEvento;

    @NotNull(message = "El ID del recinto es obligatorio")
    private Long idRecinto;

    @NotNull(message = "El ID del artista es obligatorio")
    private Long idArtista;
}