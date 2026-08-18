package com.ventaTickets.Artistas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstadoArtistaDTO {

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "^(ACTIVO|INACTIVO)$", message = "El estado debe ser ACTIVO o INACTIVO")
    private String estado;
}
