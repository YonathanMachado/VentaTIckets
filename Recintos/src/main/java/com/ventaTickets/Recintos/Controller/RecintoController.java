package com.ventaTickets.Recintos.Controller;

import com.ventaTickets.Recintos.DTO.EstadoRecintoDTO;
import com.ventaTickets.Recintos.DTO.RecintoDTO;
import com.ventaTickets.Recintos.Model.Recinto;
import com.ventaTickets.Recintos.Service.RecintoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recintos")
public class RecintoController {

    @Autowired
    private RecintoService recintoService;

    @PostMapping
    public ResponseEntity<Recinto> registrarRecinto(@Valid @RequestBody RecintoDTO dto) {
        return new ResponseEntity<>(recintoService.registrarRecinto(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Recinto>> listarRecintos() {
        return ResponseEntity.ok(recintoService.obtenerTodosActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recinto> obtenerRecinto(@PathVariable Long id) {
        return ResponseEntity.ok(recintoService.obtenerRecintoActivoPorId(id));
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Recinto>> buscarPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(recintoService.buscarPorCiudadActiva(ciudad));
    }

    @GetMapping("/capacidad/{id}")
    public ResponseEntity<Map<String, Object>> obtenerCapacidad(@PathVariable Long id) {
        return ResponseEntity.ok(Map.of("idRecinto", id, "capacidadMaxima", recintoService.obtenerCapacidad(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarDatos(
            @PathVariable Long id, 
            @Valid @RequestBody RecintoDTO dto) {
        
        recintoService.actualizarRecinto(id, dto);
        return ResponseEntity.ok("Datos del recinto actualizados correctamente.");
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable Long id, 
            @Valid @RequestBody EstadoRecintoDTO dto) {
        
        recintoService.actualizarEstado(id, dto.getEstado());
        return ResponseEntity.ok("Estado del recinto actualizado a: " + dto.getEstado());
    }
}
