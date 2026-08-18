package com.ventaTickets.Artistas.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ventaTickets.Artistas.DTO.ArtistaDTO;
import com.ventaTickets.Artistas.Model.Artista;
import com.ventaTickets.Artistas.Repository.ArtistaRepository;



@Service
public class ArtistasService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public Artista registrarArtista(ArtistaDTO dto) {
        Artista artista = new Artista();
        artista.setNombre(dto.getNombre());
        artista.setGeneroMusical(dto.getGeneroMusical());
        artista.setBiografia(dto.getBiografia());
        artista.setEstado("ACTIVO");

        artista.setFechaRegistro(LocalDateTime.now());

        return artistaRepository.save(artista);
    }

    public List<Artista> obtenerTodos() {
        return artistaRepository.findAll();
    }

    public Artista obtenerPorId(Long id) {
        return artistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artista no encontrado con ID: " + id));
    }
    
    public List<Artista> buscarPorGenero(String genero) {
        return artistaRepository.findByGeneroMusicalContainingIgnoreCase(genero);
    }

    public Artista actualizarArtista(Long id, ArtistaDTO dto) {
        Artista artistaExistente = obtenerPorId(id);
        
        artistaExistente.setNombre(dto.getNombre());
        artistaExistente.setGeneroMusical(dto.getGeneroMusical());
        artistaExistente.setBiografia(dto.getBiografia());
        
        return artistaRepository.save(artistaExistente);
    }
}
