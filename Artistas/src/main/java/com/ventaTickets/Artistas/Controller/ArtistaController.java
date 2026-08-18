package com.ventaTickets.Artistas.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Artistas.DTO.ArtistaDTO;
import com.ventaTickets.Artistas.Model.Artista;
import com.ventaTickets.Artistas.Services.ArtistasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Artista>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(artistasService.buscarPorGenero(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> actualizarArtista(@PathVariable Long id, @Valid @RequestBody ArtistaDTO dto) {
        return ResponseEntity.ok(artistasService.actualizarArtista(id, dto));
    }
}
