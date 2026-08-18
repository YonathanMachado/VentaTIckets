package com.ventaTickets.Streaming.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import com.ventaTickets.Streaming.DTO.EventoDTO;
import com.ventaTickets.Streaming.DTO.StreamingDTO;
import com.ventaTickets.Streaming.Model.Streaming;
import com.ventaTickets.Streaming.Repository.StreamingRepository;

@Service
public class StreamingService {

    @Autowired
    private StreamingRepository streamingRepository;

    @Autowired
    private WebClient webClient;

    @Value("${servicio.eventos.url}")
    private String eventosUrl;

    public Streaming crearTransmision(StreamingDTO dto) {
        if (streamingRepository.findByIdEvento(dto.getIdEvento()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este evento ya tiene una transmisión configurada.");
        }

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

        Streaming streaming = new Streaming();
        streaming.setIdEvento(dto.getIdEvento());
        streaming.setUrlAcceso(dto.getUrlAcceso());
        streaming.setPlataforma(dto.getPlataforma());
        streaming.setEstado("PROGRAMADA");
        streaming.setFechaCreacion(LocalDateTime.now());

        return streamingRepository.save(streaming);
    }

    public Streaming obtenerPorEvento(Long idEvento) {
        return streamingRepository.findByIdEvento(idEvento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay transmisión para el evento ID: " + idEvento));
    }

    public Streaming actualizarEstado(Long id, String estado) {
        Streaming streaming = streamingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transmisión no encontrada."));
        streaming.setEstado(estado.toUpperCase());
        return streamingRepository.save(streaming);
    }

    public void eliminarTransmision(Long id) {
        Streaming streaming = streamingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transmisión no encontrada."));
        streamingRepository.delete(streaming);
    }
}
