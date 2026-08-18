package com.ventaTickets.Artistas.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Artistas.DTO.ArtistaDTO;
import com.ventaTickets.Artistas.DTO.EstadoArtistaDTO;
import com.ventaTickets.Artistas.Model.Artista;
import com.ventaTickets.Artistas.Services.ArtistasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/artistas")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistasService artistasService;

    @PostMapping
    public ResponseEntity<Artista> registrarArtista(@Valid @RequestBody ArtistaDTO dto) {
        return new ResponseEntity<>(artistasService.registrarArtista(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Artista>> listarArtistas() {
        return ResponseEntity.ok(artistasService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> obtenerArtista(@PathVariable Long id) {
        return ResponseEntity.ok(artistasService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Artista>> buscarPorNombre(@RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(artistasService.buscarPorNombre(nombre));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Artista>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(artistasService.buscarPorGenero(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarDatos(@PathVariable Long id, @Valid @RequestBody ArtistaDTO dto) {
        artistasService.actualizarArtista(id, dto);
        return ResponseEntity.ok("Datos del artista actualizados correctamente.");
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<String> actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoArtistaDTO dto) {
        artistasService.actualizarEstado(id, dto.getEstado());
        return ResponseEntity.ok("Estado del artista actualizado a: " + dto.getEstado());
    }
}
