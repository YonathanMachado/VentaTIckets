package com.ventaTickets.Validacion.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventaTickets.Validacion.DTO.ValidacionDTO;
import com.ventaTickets.Validacion.Model.Validacion;
import com.ventaTickets.Validacion.Service.ValidacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/validacion")
public class ValidacionController {

    @Autowired
    private ValidacionService validacionService;

    @PostMapping("/qr")
    public ResponseEntity<Validacion> validarQr(@Valid @RequestBody ValidacionDTO dto) {
        Validacion resultado = validacionService.validarAcceso(dto);

        if (resultado.isAccesoPermitido()) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultado);
        }
    }

    @GetMapping("/ticket/{ticketId}/historial")
    public ResponseEntity<List<Validacion>> historialPorTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(validacionService.obtenerHistorialPorTicket(ticketId));
    }
}
