package com.ventaTickets.Preventa.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Preventa.DTO.PreventaDTO;
import com.ventaTickets.Preventa.Model.Preventa;
import com.ventaTickets.Preventa.Service.PreventaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/preventa")
public class PreventaController {

    @Autowired
    private PreventaService preventaService;

    @PostMapping
    public ResponseEntity<Preventa> crearPreventa(@Valid @RequestBody PreventaDTO dto) {
        return new ResponseEntity<>(preventaService.crearPreventa(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preventa> obtenerPreventa(@PathVariable Long id) {
        return ResponseEntity.ok(preventaService.obtenerPorId(id));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Preventa>> obtenerPreventasActivas() {
        return ResponseEntity.ok(preventaService.obtenerActivasAhora());
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<Preventa>> obtenerPreventasPorEvento(@PathVariable Long idEvento) {
        return ResponseEntity.ok(preventaService.obtenerPorEvento(idEvento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPreventa(@PathVariable Long id) {
        preventaService.eliminarPreventa(id);
        return ResponseEntity.noContent().build();
    }
}