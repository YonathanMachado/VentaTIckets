package com.ventaTickets.Promotores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ventaTickets.Promotores.Model.Promotores;
import com.ventaTickets.Promotores.Repository.PromotoresRepository;
import com.ventaTickets.Promotores.Service.PromotoresService;

class PromotoresServiceTest {

    @Mock
    private PromotoresRepository promotoresRepository;

    @InjectMocks
    private PromotoresService promotoresService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void actualizarEstadoPromotorDebeCambiarEstadoYGuardar() {
        Promotores promotor = new Promotores();
        promotor.setId(1L);
        promotor.setEstado("ACTIVO");

        when(promotoresRepository.findById(1L)).thenReturn(Optional.of(promotor));
        when(promotoresRepository.save(any(Promotores.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Promotores actualizado = promotoresService.actualizarEstadoPromotor(1L, "INACTIVO");

        assertEquals("INACTIVO", actualizado.getEstado());
        verify(promotoresRepository).save(promotor);
    }

    @Test
    void actualizarEstadoPromotorDebeReactivarPromotorInactivo() {
        Promotores promotor = new Promotores();
        promotor.setId(2L);
        promotor.setEstado("INACTIVO");

        when(promotoresRepository.findById(2L)).thenReturn(Optional.of(promotor));
        when(promotoresRepository.save(any(Promotores.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Promotores actualizado = promotoresService.actualizarEstadoPromotor(2L, "ACTIVO");

        assertEquals("ACTIVO", actualizado.getEstado());
        verify(promotoresRepository).save(promotor);
    }
}
