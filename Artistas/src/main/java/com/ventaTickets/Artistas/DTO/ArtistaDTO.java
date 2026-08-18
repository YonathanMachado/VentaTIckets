package com.ventaTickets.Artistas.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistaDTO {
    
    @NotBlank(message = "El nombre del artista es obligatorio")
    private String nombre;

    private String generoMusical;
    
    private String biografia;
}
