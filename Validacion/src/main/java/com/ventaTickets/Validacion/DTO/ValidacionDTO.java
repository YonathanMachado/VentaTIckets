package com.ventaTickets.Validacion.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidacionDTO {
    
    @NotBlank(message = "El código QR es obligatorio para validar")
    private String codigoQr;
}