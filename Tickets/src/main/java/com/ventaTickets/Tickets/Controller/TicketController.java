package com.ventaTickets.Tickets.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ventaTickets.Tickets.DTO.TicketDTO;
import com.ventaTickets.Tickets.Model.Ticket;
import com.ventaTickets.Tickets.Service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> comprarTicket(@Valid @RequestBody TicketDTO dto) {
        return new ResponseEntity<>(ticketService.generarTicket(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.obtenerPorId(id));
    }

    @GetMapping("/evento/{eventoId}/disponibles")
    public ResponseEntity<List<Ticket>> obtenerDisponibles(@PathVariable Long eventoId) {
        return ResponseEntity.ok(ticketService.obtenerDisponiblesPorEvento(eventoId));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Ticket>> buscarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ticketService.buscarPorUsuario(idUsuario));
    }

    @PatchMapping("/{id}/bloquear")
    public ResponseEntity<Ticket> bloquearTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.bloquearTicket(id));
    }

    @PatchMapping("/{id}/liberar")
    public ResponseEntity<Ticket> liberarTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.liberarTicket(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicketSiPuede(id);
        return ResponseEntity.noContent().build();
    }
}