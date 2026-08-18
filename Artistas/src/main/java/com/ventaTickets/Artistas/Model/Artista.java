package com.ventaTickets.Artistas.Model;

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
@Table (name = "artistas")
@AllArgsConstructor
@NoArgsConstructor
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    @Column(name = "genero_musical")
    private String generoMusical;
    
    private String biografia;
    
    private String estado;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}

