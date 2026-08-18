package com.ventaTickets.Preventa.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.ventaTickets.Preventa.DTO.EventoDTO;
import com.ventaTickets.Preventa.DTO.PreventaDTO;
import com.ventaTickets.Preventa.Model.Preventa;
import com.ventaTickets.Preventa.Repository.PreventaRepository;

@Service
public class PreventaService {

    @Autowired
    private PreventaRepository preventaRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.eventos.url}")
    private String eventosUrl;

    public Preventa crearPreventa(PreventaDTO dto) {

        if (dto.getFechaInicio().isAfter(dto.getFechaFin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        try {
            webClient.get()
                    .uri(eventosUrl + "/" + dto.getIdEvento())
                    .retrieve()
                    .bodyToMono(EventoDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El evento ID " + dto.getIdEvento() + " no existe.");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error de comunicación con el microservicio de Eventos.");
        }

        Preventa preventa = new Preventa();
        preventa.setIdEvento(dto.getIdEvento());
        preventa.setNombre(dto.getNombre());
        preventa.setPrecio(dto.getPrecio());
        preventa.setFechaInicio(dto.getFechaInicio());
        preventa.setFechaFin(dto.getFechaFin());
        preventa.setLimiteTickets(dto.getLimiteTickets());
        preventa.setTicketsVendidos(0);
        preventa.setEstado("ACTIVA");

        return preventaRepository.save(preventa);
    }

    public Preventa obtenerPorId(Long id) {
        return preventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Preventa no encontrada."));
    }

    public List<Preventa> obtenerPorEvento(Long idEvento) {
        return preventaRepository.findByIdEvento(idEvento);
    }

    public List<Preventa> obtenerActivasAhora() {
        LocalDateTime ahora = LocalDateTime.now();
        return preventaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(ahora, ahora);
    }

    public void eliminarPreventa(Long id) {
        Preventa preventa = obtenerPorId(id);
        preventaRepository.delete(preventa);
    }
}
