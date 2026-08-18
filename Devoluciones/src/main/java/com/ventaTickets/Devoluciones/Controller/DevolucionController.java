package com.ventaTickets.Devoluciones.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ventaTickets.Devoluciones.DTO.DevolucionesDTO;
import com.ventaTickets.Devoluciones.Model.Devoluciones;
import com.ventaTickets.Devoluciones.Service.DevolucionesService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionesService devolucionService;

    @PostMapping
    public ResponseEntity<Devoluciones> crearSolicitud(@Valid @RequestBody DevolucionesDTO dto) {
        return new ResponseEntity<>(devolucionService.solicitarDevolucion(dto), HttpStatus.CREATED);
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<Devoluciones>> obtenerPorVenta(@PathVariable Long ventaId) {
        return ResponseEntity.ok(devolucionService.obtenerPorVenta(ventaId));
    }

    @PatchMapping("/estadodev/{id}")
    public ResponseEntity<Devoluciones> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(devolucionService.actualizarEstado(id, body.get("estado")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long id) {
        devolucionService.eliminarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}
