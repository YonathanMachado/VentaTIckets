package com.ventaTickets.Recintos.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recintos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String ciudad;
    
    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;
    
    private String estado;
}
