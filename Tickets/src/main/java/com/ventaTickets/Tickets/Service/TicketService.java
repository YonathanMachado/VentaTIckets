package com.ventaTickets.Tickets.Service;

import com.ventaTickets.Tickets.DTO.EventoDTO;
import com.ventaTickets.Tickets.DTO.TicketDTO;
import com.ventaTickets.Tickets.Model.Ticket;
import com.ventaTickets.Tickets.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.eventos.url}")
    private String eventosUrl;

    public Ticket generarTicket(TicketDTO dto) {
        try {
            webClient.get()
                    .uri(eventosUrl + "/" + dto.getIdEvento())
                    .retrieve()
                    .bodyToMono(EventoDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El evento ID " + dto.getIdEvento() + " no existe.");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error de comunicación con ms-eventos.");
        }

        Ticket ticket = new Ticket();
        ticket.setIdEvento(dto.getIdEvento());
        ticket.setIdUsuario(dto.getIdUsuario());
        ticket.setPrecio(dto.getPrecio());
        ticket.setEstado("VENDIDO");
        ticket.setFechaCompra(LocalDateTime.now());
        ticket.setCodigoQr("QR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return ticketRepository.save(ticket);
    }

    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));
    }

    public Ticket obtenerPorIdYValidar(Long id) {
        return obtenerPorId(id);
    }

    public List<Ticket> buscarPorUsuario(Long idUsuario) {
        return ticketRepository.findByIdUsuario(idUsuario);
    }

    public List<Ticket> obtenerDisponiblesPorEvento(Long eventoId) {
        return ticketRepository.findByIdEventoAndEstado(eventoId, "VENDIDO");
    }

    public Ticket bloquearTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (!"VENDIDO".equals(ticket.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El ticket no está disponible para bloquear.");
        }
        ticket.setEstado("BLOQUEADO");
        return ticketRepository.save(ticket);
    }

    public Ticket liberarTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (!"BLOQUEADO".equals(ticket.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El ticket no está bloqueado.");
        }
        ticket.setEstado("VENDIDO");
        return ticketRepository.save(ticket);
    }

    public void eliminarTicketSiPuede(Long id) {
        Ticket ticket = obtenerPorId(id);
        if (!"VENDIDO".equals(ticket.getEstado()) && !"DISPONIBLE".equals(ticket.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar un ticket comprado o asignado.");
        }
        ticketRepository.delete(ticket);
    }
}
