package com.ventaTickets.Validacion.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "validaciones")
@AllArgsConstructor
@NoArgsConstructor
public class Validacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_qr")
    private String codigoQr;

    private boolean accesoPermitido; // true si entró, false si se rechazó
    
    private String mensaje; // Ej: "Acceso concedido", "Ticket ya usado", "QR Inválido"

    @Column(name = "fecha_validacion")
    private LocalDateTime fechaValidacion;
}