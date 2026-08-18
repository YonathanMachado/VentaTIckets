package com.ventaTickets.Streaming;

import com.ventaTickets.Streaming.Model.Streaming;
import com.ventaTickets.Streaming.Repository.StreamingRepository;
import com.ventaTickets.Streaming.Service.StreamingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreamingApplicationTests {

    @Mock
    private StreamingRepository streamingRepository;

    @InjectMocks
    private StreamingService streamingService;

    private Streaming streamingMock;

    @BeforeEach
    void setUp() {
        streamingMock = new Streaming();
        streamingMock.setId(1L);
        streamingMock.setIdEvento(10L);
        streamingMock.setUrlAcceso("https://stream.test.com/evento10");
        streamingMock.setPlataforma("YouTube");
        streamingMock.setEstado("PROGRAMADA");
        streamingMock.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void obtenerPorEvento_cuandoExiste_retornaStreaming() {
        // Given
        when(streamingRepository.findByIdEvento(10L)).thenReturn(Optional.of(streamingMock));

        // When
        Streaming resultado = streamingService.obtenerPorEvento(10L);

        // Then
        assertNotNull(resultado);
        assertEquals("YouTube", resultado.getPlataforma());
        assertEquals("PROGRAMADA", resultado.getEstado());
    }

    @Test
    void obtenerPorEvento_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(streamingRepository.findByIdEvento(99L)).thenReturn(Optional.empty());

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> streamingService.obtenerPorEvento(99L));
        assertEquals(404, ex.getStatusCode().value());
    }

    @Test
    void streaming_estadoInicial_debeSerProgramada() {
        // Given & When & Then
        assertEquals("PROGRAMADA", streamingMock.getEstado(),
                "El estado inicial de una transmisión debe ser PROGRAMADA");
    }

    @Test
    void streaming_urlAcceso_noDebeSerNula() {
        // Given & When & Then
        assertNotNull(streamingMock.getUrlAcceso(),
                "La URL de acceso al streaming no puede ser nula");
    }
}
