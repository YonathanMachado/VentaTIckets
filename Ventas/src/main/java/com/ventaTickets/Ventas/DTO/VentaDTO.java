package com.ventaTickets.Ventas.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VentaDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El ID del evento es obligatorio")
    private Long idEvento;

    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal montoTotal;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}
