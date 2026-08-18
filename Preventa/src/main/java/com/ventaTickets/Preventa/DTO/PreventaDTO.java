package com.ventaTickets.Preventa.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreventaDTO {
    
    @NotNull(message = "El ID del evento es obligatorio")
    private Long idEvento;

    @NotBlank(message = "El nombre de la preventa es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precio;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe estar en el futuro")
    private LocalDateTime fechaFin;

    @NotNull(message = "El límite de tickets es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 ticket disponible")
    private Integer limiteTickets;

    // Lombok generates getters/setters
}
