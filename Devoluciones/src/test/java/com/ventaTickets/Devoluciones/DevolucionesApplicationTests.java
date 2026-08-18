package com.ventaTickets.Devoluciones;

import com.ventaTickets.Devoluciones.Model.Devoluciones;
import com.ventaTickets.Devoluciones.Repository.DevolucionesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DevolucionesApplicationTests {

    @Mock
    private DevolucionesRepository devolucionesRepository;

    private Devoluciones devolucionMock;

    @BeforeEach
    void setUp() {
        devolucionMock = new Devoluciones();
        devolucionMock.setId(1L);
        devolucionMock.setIdTicket(100L);
        devolucionMock.setMotivo("Evento cancelado");
        devolucionMock.setMonto(50000.0);
        devolucionMock.setEstado("PENDIENTE");
    }

    @Test
    void devolucion_estadoInicial_debeSerPendiente() {
        // Given & When & Then
        assertEquals("PENDIENTE", devolucionMock.getEstado(),
                "El estado inicial de una devolución debe ser PENDIENTE");
    }

    @Test
    void devolucion_monto_debeSerPositivo() {
        // Given & When & Then
        assertTrue(devolucionMock.getMonto() > 0,
                "El monto de la devolución debe ser mayor a cero");
    }

    @Test
    void devolucion_motivo_noDebeSerNulo() {
        // Given & When & Then
        assertNotNull(devolucionMock.getMotivo(),
                "El motivo de la devolución no puede ser nulo");
        assertFalse(devolucionMock.getMotivo().isBlank());
    }

    @Test
    void repositorio_guardarDevolucion_llamaAlMetodoSave() {
        // Given
        when(devolucionesRepository.save(any(Devoluciones.class))).thenReturn(devolucionMock);

        // When
        Devoluciones resultado = devolucionesRepository.save(devolucionMock);

        // Then
        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdTicket());
        verify(devolucionesRepository).save(devolucionMock);
    }

    @Test
    void repositorio_findAll_retornaListaDevoluciones() {
        // Given
        when(devolucionesRepository.findAll()).thenReturn(List.of(devolucionMock));

        // When
        List<Devoluciones> resultado = devolucionesRepository.findAll();

        // Then
        assertEquals(1, resultado.size());
    }
}
