package com.ventaTickets.Preventa.Model;

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

@Entity 
@Table(name = "preventas")
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class Preventa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_evento")
    private Long idEvento;

    private String nombre;
    private double precio;
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @Column(name = "limite_tickets")
    private Integer limiteTickets;
    
    @Column(name = "tickets_vendidos")
    private Integer ticketsVendidos;
    
    private String estado;
}
