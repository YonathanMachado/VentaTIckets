package com.ventaTickets.Streaming.Model;

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
@Table(name = "transmisiones")
@NoArgsConstructor
@AllArgsConstructor
public class Streaming {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_evento", unique = true)
    private Long idEvento;

    @Column(name = "url_acceso")
    private String urlAcceso;
    
    private String plataforma; // ej. "ZOOM", "YOUTUBE", "VIMEO"
    
    private String estado; // "PROGRAMADA", "EN_VIVO", "FINALIZADA"

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
