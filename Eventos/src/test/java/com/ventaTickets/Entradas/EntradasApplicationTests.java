package com.ventaTickets.Entradas;

import com.ventaTickets.Eventos.DTO.EventoDTO;
import com.ventaTickets.Eventos.Model.Evento;
import com.ventaTickets.Eventos.Rrepository.EventoRepository;
import com.ventaTickets.Eventos.Service.EventoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntradasApplicationTests {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    private Evento eventoMock;

    @BeforeEach
    void setUp() {
        eventoMock = new Evento();
        eventoMock.setId(1L);
        eventoMock.setNombre("Festival de Rock 2025");
        eventoMock.setDescripcion("Gran festival de música rock");
        eventoMock.setFechaEvento(LocalDateTime.of(2025, 12, 15, 20, 0));
        eventoMock.setIdRecinto(1L);
        eventoMock.setIdArtista(1L);
        eventoMock.setEstado("PROGRAMADO");
    }

    @Test
    void crearEvento_dadosDTOValidos_retornaEventoConEstadoProgramado() {
        // Given
        EventoDTO dto = new EventoDTO();
        dto.setNombre("Festival de Rock 2025");
        dto.setDescripcion("Gran festival de música rock");
        dto.setFechaEvento(LocalDateTime.of(2025, 12, 15, 20, 0));
        dto.setIdRecinto(1L);
        dto.setIdArtista(1L);
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoMock);

        // When
        Evento resultado = eventoService.crearEvento(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("PROGRAMADO", resultado.getEstado());
        assertEquals("Festival de Rock 2025", resultado.getNombre());
        verify(eventoRepository).save(any(Evento.class));
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaEvento() {
        // Given
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(eventoMock));

        // When
        Evento resultado = eventoService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertNotNull(resultado.getFechaEvento());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundConMensaje() {
        // Given
        when(eventoRepository.findById(50L)).thenReturn(Optional.empty());

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> eventoService.obtenerPorId(50L));
        assertEquals(404, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("Evento no encontrado"));
    }

    @Test
    void obtenerTodos_retornaListaDeEventos() {
        // Given
        Evento evento2 = new Evento();
        evento2.setId(2L);
        evento2.setNombre("Concierto de Jazz");
        when(eventoRepository.findAll()).thenReturn(List.of(eventoMock, evento2));

        // When
        List<Evento> resultado = eventoService.obtenerTodos();

        // Then
        assertEquals(2, resultado.size());
    }
}
