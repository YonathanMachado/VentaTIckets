package com.ventaTickets.Tickets.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_evento")
    private Long idEvento;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "codigo_qr", unique = true)
    private String codigoQr; 

    private Double precio;
    
    private String estado; 

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;
}
