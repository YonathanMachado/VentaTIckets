package com.ventaTickets.Devoluciones.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.ventaTickets.Devoluciones.DTO.DevolucionesDTO;
import com.ventaTickets.Devoluciones.DTO.TicketResponseDTO;
import com.ventaTickets.Devoluciones.Model.Devoluciones;
import com.ventaTickets.Devoluciones.Repository.DevolucionesRepository;

@Service
public class DevolucionesService {

    @Autowired
    private DevolucionesRepository devolucionRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.tickets.url}")
    private String ticketServiceUrl;

    public Devoluciones solicitarDevolucion(DevolucionesDTO dto) {
        TicketResponseDTO ticketInfo;
        try {
            ticketInfo = webClient.get()
                    .uri(ticketServiceUrl + "/" + dto.getIdTicket())
                    .retrieve()
                    .bodyToMono(TicketResponseDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El ticket ID " + dto.getIdTicket() + " no existe.");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de comunicación con Tickets: " + ex.getMessage());
        }

        if (ticketInfo != null && ("USADO".equals(ticketInfo.getEstado()) || "CANCELADO".equals(ticketInfo.getEstado()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No se puede devolver un ticket con estado: " + ticketInfo.getEstado());
        }

        Devoluciones devolucion = new Devoluciones();
        devolucion.setIdTicket(dto.getIdTicket());
        devolucion.setMotivo(dto.getMotivo());
        devolucion.setMonto(dto.getMonto());
        devolucion.setEstado("PENDIENTE");

        return devolucionRepository.save(devolucion);
    }

    public List<Devoluciones> obtenerTodas() {
        return devolucionRepository.findAll();
    }

    public List<Devoluciones> obtenerPorVenta(Long ventaId) {
        return devolucionRepository.findByIdTicket(ventaId);
    }

    public Devoluciones actualizarEstado(Long id, String estado) {
        Devoluciones devolucion = devolucionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud de devolución no encontrada."));
        devolucion.setEstado(estado.toUpperCase());
        return devolucionRepository.save(devolucion);
    }

    public void eliminarSolicitud(Long id) {
        Devoluciones devolucion = devolucionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud de devolución no encontrada."));
        if (!"PENDIENTE".equalsIgnoreCase(devolucion.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se pueden eliminar solicitudes pendientes.");
        }
        devolucionRepository.delete(devolucion);
    }
}
