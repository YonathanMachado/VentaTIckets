package com.ventaTickets.Eventos.Rrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ventaTickets.Eventos.Model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByIdArtista(Long idArtista);
    List<Evento> findByIdRecinto(Long idRecinto);
}

