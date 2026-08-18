package com.ventaTickets.Recintos.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RecintoDTO {
    
    @NotBlank(message = "El nombre del recinto es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private Integer capacidadMaxima;
}