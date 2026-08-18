package com.ventaTickets.Ventas.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "id_ticket_generado")
    private Long idTicketGenerado; // Guardamos qué ticket se le entregó

    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    
    @Column(name = "metodo_pago")
    private String metodoPago; // "DEBITO", "CREDITO", "EFECTIVO"

    private String estado; // "COMPLETADA", "RECHAZADA"

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;
}
