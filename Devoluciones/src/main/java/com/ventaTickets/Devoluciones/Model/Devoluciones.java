package com.ventaTickets.Devoluciones.Model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devoluciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devoluciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 200, nullable = false)
    private String motivo;

    @Column(nullable = false)
    private Double monto;

    @Column(length = 10, nullable = false)
    private String estado; //pendiente, aprobada, rechazada

    @Column(nullable = false, insertable = false, updatable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private LocalDate fechaResolucion;

    @NotNull
    @Column(name = "id_ticket")
    private Long idTicket;
}
