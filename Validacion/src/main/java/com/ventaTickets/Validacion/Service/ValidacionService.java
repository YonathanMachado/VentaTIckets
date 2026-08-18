package com.ventaTickets.Validacion.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.ventaTickets.Validacion.DTO.TicketDTO;
import com.ventaTickets.Validacion.DTO.ValidacionDTO;
import com.ventaTickets.Validacion.Model.Validacion;
import com.ventaTickets.Validacion.Repository.ValidacionRepository;

@Service
public class ValidacionService {

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.tickets.url}")
    private String ticketsUrl;

    public Validacion validarAcceso(ValidacionDTO request) {
        Validacion logValidacion = new Validacion();
        logValidacion.setCodigoQr(request.getCodigoQr());
        logValidacion.setFechaValidacion(LocalDateTime.now());

        try {
            TicketDTO ticket = webClient.get()
                    .uri(ticketsUrl + "/qr/" + request.getCodigoQr())
                    .retrieve()
                    .bodyToMono(TicketDTO.class)
                    .block();

            if (ticket != null && "VENDIDO".equals(ticket.getEstado())) {
                logValidacion.setAccesoPermitido(true);
                logValidacion.setMensaje("Acceso concedido. ¡Bienvenido!");
            } else {
                logValidacion.setAccesoPermitido(false);
                logValidacion.setMensaje("Acceso denegado. El ticket tiene estado: " + (ticket != null ? ticket.getEstado() : "DESCONOCIDO"));
            }

        } catch (WebClientResponseException.NotFound ex) {
            logValidacion.setAccesoPermitido(false);
            logValidacion.setMensaje("Acceso denegado. Código QR no existe en el sistema.");
        } catch (Exception ex) {
            logValidacion.setAccesoPermitido(false);
            logValidacion.setMensaje("Error de sistema al validar el ticket.");
        }

        return validacionRepository.save(logValidacion);
    }

    public List<Validacion> obtenerHistorialPorTicket(Long ticketId) {
        return validacionRepository.findByCodigoQr("QR-" + ticketId);
    }
}