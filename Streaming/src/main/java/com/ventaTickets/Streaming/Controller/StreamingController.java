package com.ventaTickets.Streaming.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Streaming.DTO.StreamingDTO;
import com.ventaTickets.Streaming.Model.Streaming;
import com.ventaTickets.Streaming.Service.StreamingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/streaming")
public class StreamingController {

    @Autowired
    private StreamingService streamingService;

    @PostMapping
    public ResponseEntity<Streaming> configurarTransmision(@Valid @RequestBody StreamingDTO dto) {
        return new ResponseEntity<>(streamingService.crearTransmision(dto), HttpStatus.CREATED);
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<Streaming> obtenerPorEvento(@PathVariable Long idEvento) {
        return ResponseEntity.ok(streamingService.obtenerPorEvento(idEvento));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Streaming> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(streamingService.actualizarEstado(id, body.get("estado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransmision(@PathVariable Long id) {
        streamingService.eliminarTransmision(id);
        return ResponseEntity.noContent().build();
    }
}