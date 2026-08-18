package com.ventaTickets.Eventos.Model;

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
@Table(name = "eventos")
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;

    @Column(name = "id_recinto")
    private Long idRecinto;

    @Column(name = "id_artista")
    private Long idArtista;

    private String estado; // "PROGRAMADO", "CANCELADO", "FINALIZADO"
}
