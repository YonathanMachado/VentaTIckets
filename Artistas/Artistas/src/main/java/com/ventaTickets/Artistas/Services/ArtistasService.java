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
        return artistaRepository.findAll().stream()
                .filter(artista -> "ACTIVO".equalsIgnoreCase(artista.getEstado()))
                .toList();
    }

    public Artista obtenerPorId(Long id) {
        return artistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artista no encontrado con ID: " + id));
    }

    public Artista obtenerActivoPorId(Long id) {
        Artista artista = obtenerPorId(id);
        if (!"ACTIVO".equalsIgnoreCase(artista.getEstado())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El artista no está disponible (INACTIVO).");
        }
        return artista;
    }

    public List<Artista> buscarPorGenero(String genero) {

        return artistaRepository.findByGeneroMusicalContainingIgnoreCase(genero).stream()
                .filter(artista -> "ACTIVO".equalsIgnoreCase(artista.getEstado()))
                .toList();
    }

    public List<Artista> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return List.of();
        }
        return artistaRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .filter(artista -> "ACTIVO".equalsIgnoreCase(artista.getEstado()))
                .toList();
    }

    public Artista actualizarArtista(Long id, ArtistaDTO dto) {
        Artista artistaExistente = obtenerPorId(id);

        artistaExistente.setNombre(dto.getNombre());
        artistaExistente.setGeneroMusical(dto.getGeneroMusical());
        artistaExistente.setBiografia(dto.getBiografia());

        return artistaRepository.save(artistaExistente);
    }

    public void desactivarArtista(Long id) {
        actualizarEstado(id, "INACTIVO");
    }

    public Artista actualizarEstado(Long id, String estado) {
        String estadoNormalizado = estado != null ? estado.toUpperCase() : "";

        if (!"ACTIVO".equals(estadoNormalizado) && !"INACTIVO".equals(estadoNormalizado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVO o INACTIVO");
        }

        Artista artista = obtenerPorId(id);
        artista.setEstado(estadoNormalizado);
        return artistaRepository.save(artista);
    }
}
