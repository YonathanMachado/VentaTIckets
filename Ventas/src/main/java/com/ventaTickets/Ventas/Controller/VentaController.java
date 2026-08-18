package com.ventaTickets.Ventas.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ventaTickets.Ventas.DTO.VentaDTO;
import com.ventaTickets.Ventas.Model.Venta;
import com.ventaTickets.Ventas.Service.VentaService;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<Venta> realizarCompra(@Valid @RequestBody VentaDTO dto) {
        return new ResponseEntity<>(ventaService.procesarCompra(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Venta>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ventaService.obtenerPorUsuario(usuarioId));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Venta>> obtenerPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(ventaService.obtenerPorEvento(eventoId));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<Venta> anularVenta(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.anularVenta(id));
    }
}
