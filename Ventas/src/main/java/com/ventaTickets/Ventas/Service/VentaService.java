package com.ventaTickets.Ventas.Service;

import com.ventaTickets.Ventas.DTO.TicketResponseDTO;
import com.ventaTickets.Ventas.DTO.VentaDTO;
import com.ventaTickets.Ventas.Model.Venta;
import com.ventaTickets.Ventas.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.tickets.url}")
    private String ticketsUrl;

    public Venta procesarCompra(VentaDTO request) {
        Venta venta = new Venta();
        venta.setIdUsuario(request.getIdUsuario());
        venta.setIdEvento(request.getIdEvento());
        venta.setMontoTotal(request.getMontoTotal());
        venta.setMetodoPago(request.getMetodoPago());
        venta.setFechaVenta(LocalDateTime.now());

        try {
            Map<String, Object> ticketRequest = new HashMap<>();
            ticketRequest.put("idEvento", request.getIdEvento());
            ticketRequest.put("idUsuario", request.getIdUsuario());
            ticketRequest.put("precio", request.getMontoTotal());

            TicketResponseDTO ticketGenerado = webClient.post()
                    .uri(ticketsUrl)
                    .bodyValue(ticketRequest)
                    .retrieve()
                    .bodyToMono(TicketResponseDTO.class)
                    .block();

            venta.setEstado("COMPLETADA");
            venta.setIdTicketGenerado(ticketGenerado.getId());

        } catch (Exception ex) {
            venta.setEstado("RECHAZADA");
            ventaRepository.save(venta);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo generar el ticket. Compra rechazada.");
        }

        return ventaRepository.save(venta);
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta no encontrada."));
    }

    public List<Venta> obtenerPorUsuario(Long usuarioId) {
        return ventaRepository.findByIdUsuario(usuarioId);
    }

    public List<Venta> obtenerPorEvento(Long eventoId) {
        return ventaRepository.findByIdEvento(eventoId);
    }

    public Venta anularVenta(Long id) {
        Venta venta = obtenerVentaPorId(id);
        venta.setEstado("ANULADA");
        return ventaRepository.save(venta);
    }
}
