package com.ventaTickets.Eventos.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Eventos.DTO.EstadoEventoDTO;
import com.ventaTickets.Eventos.DTO.EventoDTO;
import com.ventaTickets.Eventos.Model.Evento;
import com.ventaTickets.Eventos.Service.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> crear(@Valid @RequestBody EventoDTO dto) {
        return ResponseEntity.ok(eventoService.crearEvento(dto));
    }

    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<Evento>> historialArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(eventoService.obtenerPorArtista(artistaId));
    }

    @GetMapping("/recinto/{recintoId}")
    public ResponseEntity<List<Evento>> carteleraRecinto(@PathVariable Long recintoId) {
        return ResponseEntity.ok(eventoService.obtenerPorRecinto(recintoId));
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<Evento> cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoEventoDTO dto) {
        return ResponseEntity.ok(eventoService.actualizarEstadoEvento(id, dto.getEstado()));
    }

    @GetMapping("/recinto") public ResponseEntity<List<Evento>> carteleraTodos() {
    return ResponseEntity.ok(eventoService.obtenerPorRecinto(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoService.eliminarSiEsBorrador(id);
        return ResponseEntity.noContent().build();
    }
}