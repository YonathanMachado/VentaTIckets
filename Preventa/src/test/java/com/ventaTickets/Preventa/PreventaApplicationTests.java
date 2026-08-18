package com.ventaTickets.Preventa;

import com.ventaTickets.Preventa.DTO.PreventaDTO;
import com.ventaTickets.Preventa.Model.Preventa;
import com.ventaTickets.Preventa.Repository.PreventaRepository;
import com.ventaTickets.Preventa.Service.PreventaService;
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
class PreventaApplicationTests {

    @Mock
    private PreventaRepository preventaRepository;

    @InjectMocks
    private PreventaService preventaService;

    private Preventa preventaMock;

    @BeforeEach
    void setUp() {
        preventaMock = new Preventa();
        preventaMock.setId(1L);
        preventaMock.setIdEvento(5L);
        preventaMock.setNombre("Preventa Early Bird");
        preventaMock.setPrecio(30000.0);
        preventaMock.setFechaInicio(LocalDateTime.of(2025, 10, 1, 0, 0));
        preventaMock.setFechaFin(LocalDateTime.of(2025, 10, 15, 23, 59));
        preventaMock.setLimiteTickets(100);
        preventaMock.setTicketsVendidos(0);
        preventaMock.setEstado("ACTIVA");
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaPreventa() {
        // Given
        when(preventaRepository.findById(1L)).thenReturn(Optional.of(preventaMock));

        // When
        Preventa resultado = preventaService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Preventa Early Bird", resultado.getNombre());
        assertEquals("ACTIVA", resultado.getEstado());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(preventaRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResponseStatusException.class, () -> preventaService.obtenerPorId(99L));
    }

    @Test
    void obtenerPorEvento_retornaPreventasDelEvento() {
        // Given
        when(preventaRepository.findByIdEvento(5L)).thenReturn(List.of(preventaMock));

        // When
        List<Preventa> resultado = preventaService.obtenerPorEvento(5L);

        // Then
        assertEquals(1, resultado.size());
        assertEquals(5L, resultado.get(0).getIdEvento());
    }

    @Test
    void preventa_fechaInicio_noDebeSerPosteriorAFechaFin() {
        // Given - regla de negocio crítica
        LocalDateTime inicio = preventaMock.getFechaInicio();
        LocalDateTime fin = preventaMock.getFechaFin();

        // When & Then
        assertFalse(inicio.isAfter(fin),
                "La fecha de inicio de una preventa no puede ser posterior a la fecha de fin");
    }

    @Test
    void preventa_ticketsVendidosInicial_debeSer0() {
        // Given & When & Then
        assertEquals(0, preventaMock.getTicketsVendidos(),
                "Los tickets vendidos al crear una preventa deben iniciar en 0");
    }
}
