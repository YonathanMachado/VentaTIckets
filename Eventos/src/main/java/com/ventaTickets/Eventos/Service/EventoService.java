package com.ventaTickets.Eventos.Service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import com.ventaTickets.Eventos.DTO.EventoDTO;
import com.ventaTickets.Eventos.Model.Evento;
import com.ventaTickets.Eventos.Rrepository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Evento crearEvento(EventoDTO dto) {

    try {
        webClientBuilder.build()
            .get()
            .uri("http://localhost:8086/api/artistas/" + dto.getIdArtista())
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    } catch (WebClientResponseException.NotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El artista con ID " + dto.getIdArtista() + " no existe.");
    } catch (Exception e) {
        
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El servicio de Artistas no está disponible.");
    }

    try {
        webClientBuilder.build()
            .get()
            .uri("http://localhost:8082/api/recintos/" + dto.getIdRecinto())
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    } catch (WebClientResponseException.NotFound e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El recinto con ID " + dto.getIdRecinto() + " no existe.");
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El servicio de Recintos no está disponible.");
    }

        Evento evento = new Evento();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaEvento(dto.getFechaEvento());
        evento.setIdRecinto(dto.getIdRecinto());
        evento.setIdArtista(dto.getIdArtista());
        evento.setEstado("BORRADOR");
        return eventoRepository.save(evento);
    }

    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado con ID: " + id));
    }

    public List<Evento> obtenerPorArtista(Long artistaId) {
        return eventoRepository.findByIdArtista(artistaId);
    }

    public List<Evento> obtenerPorRecinto(Long recintoId) {
        if (recintoId != null) {
            return eventoRepository.findByIdRecinto(recintoId);
        }
        return eventoRepository.findAll();
    }

    public Evento actualizarEstadoEvento(Long id, String estado) {
        Evento evento = obtenerPorId(id);
        String estadoUpper = estado.toUpperCase();

        if (!estadoUpper.equals("PROGRAMADO") && 
            !estadoUpper.equals("CANCELADO") && 
            !estadoUpper.equals("FINALIZADO")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido. Use PROGRAMADO, CANCELADO o FINALIZADO.");
        }

        evento.setEstado(estadoUpper);
        return eventoRepository.save(evento);
    }

    public void eliminarSiEsBorrador(Long id) {
        Evento evento = obtenerPorId(id);
        if (!"BORRADOR".equalsIgnoreCase(evento.getEstado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se pueden eliminar eventos en estado BORRADOR. Use CANCELADO para eventos ya publicados.");
        }
        eventoRepository.delete(evento);
    }
}