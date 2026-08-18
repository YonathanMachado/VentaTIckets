package com.ventaTickets.Promotores.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Promotores.DTO.EstadoPromotorDTO;
import com.ventaTickets.Promotores.DTO.PromotoresDTO;
import com.ventaTickets.Promotores.Model.Promotores;
import com.ventaTickets.Promotores.Service.PromotoresService;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/promotores")
public class PromotoresController {
    
    private final PromotoresService promotorService;

    public PromotoresController(PromotoresService promotorService) {
        this.promotorService = promotorService;
    }

    @PostMapping
    public ResponseEntity<Promotores> registrarPromotor(@Valid @RequestBody PromotoresDTO dto) {
        return new ResponseEntity<>(promotorService.registrarPromotor(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Promotores>> listarPromotores() {
        return ResponseEntity.ok(promotorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotores> obtenerPromotor(@PathVariable Long id) {
        return ResponseEntity.ok(promotorService.obtenerPorId(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Promotores> validarCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(promotorService.obtenerPorCodigo(codigo));
    }

    @GetMapping("/rendimiento/{id}")
    public ResponseEntity<Map<String, Object>> obtenerRendimiento(@PathVariable Long id) {
        return ResponseEntity.ok(promotorService.obtenerRendimiento(id));
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<Promotores> actualizarEstadoPromotor(@PathVariable Long id,
            @Valid @RequestBody EstadoPromotorDTO dto) {
        return ResponseEntity.ok(promotorService.actualizarEstadoPromotor(id, dto.getEstado()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarDatos(@PathVariable Long id, 
        @Valid @RequestBody PromotoresDTO dto) {
        
        promotorService.actualizarPromotor(id, dto);
        return ResponseEntity.ok("Datos del promotor actualizados correctamente.");
    }
}
