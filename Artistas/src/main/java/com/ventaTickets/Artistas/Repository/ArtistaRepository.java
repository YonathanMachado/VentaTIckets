package com.ventaTickets.Artistas.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Artistas.Model.Artista;

@Repository

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    List<Artista> findByGeneroMusicalContainingIgnoreCase(String genero);
}
