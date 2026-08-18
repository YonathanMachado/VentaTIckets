package com.ventaTickets.Promotores;

import com.ventaTickets.Promotores.DTO.PromotoresDTO;
import com.ventaTickets.Promotores.Model.Promotores;
import com.ventaTickets.Promotores.Repository.PromotoresRepository;
import com.ventaTickets.Promotores.Service.PromotoresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotoresApplicationTests {

    @Mock
    private PromotoresRepository promotoresRepository;

    @InjectMocks
    private PromotoresService promotoresService;

    private Promotores promotorMock;

    @BeforeEach
    void setUp() {
        promotorMock = new Promotores();
        promotorMock.setId(1L);
        promotorMock.setNombre("Promotor Test");
        promotorMock.setEmail("promotor@test.com");
        promotorMock.setCodigoPromocional("PROMO2025");
        promotorMock.setDescuentoPorcentaje(10.0);
        promotorMock.setComisionPorcentaje(5.0);
        promotorMock.setEstado("ACTIVO");
    }

    @Test
    void registrarPromotor_emailNuevo_retornaPromotorGuardado() {
        // Given
        PromotoresDTO dto = new PromotoresDTO();
        dto.setNombre("Promotor Test");
        dto.setEmail("promotor@test.com");
        dto.setCodigoPromocional("promo2025");
        dto.setDescuentoPorcentaje(10.0);
        dto.setComisionPorcentaje(5.0);

        when(promotoresRepository.existsByEmail("promotor@test.com")).thenReturn(false);
        when(promotoresRepository.existsByCodigoPromocional("promo2025")).thenReturn(false);
        when(promotoresRepository.save(any(Promotores.class))).thenReturn(promotorMock);

        // When
        Promotores resultado = promotoresService.registrarPromotor(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals("PROMO2025", resultado.getCodigoPromocional());
    }

    @Test
    void registrarPromotor_emailDuplicado_lanzaConflictException() {
        // Given
        PromotoresDTO dto = new PromotoresDTO();
        dto.setEmail("promotor@test.com");
        dto.setCodigoPromocional("NUEVO");
        when(promotoresRepository.existsByEmail("promotor@test.com")).thenReturn(true);

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> promotoresService.registrarPromotor(dto));
        assertEquals(409, ex.getStatusCode().value());
    }

    @Test
    void registrarPromotor_codigoDuplicado_lanzaConflictException() {
        // Given
        PromotoresDTO dto = new PromotoresDTO();
        dto.setEmail("nuevo@email.com");
        dto.setCodigoPromocional("PROMO2025");
        when(promotoresRepository.existsByEmail("nuevo@email.com")).thenReturn(false);
        when(promotoresRepository.existsByCodigoPromocional("PROMO2025")).thenReturn(true);

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> promotoresService.registrarPromotor(dto));
        assertEquals(409, ex.getStatusCode().value());
    }

    @Test
    void obtenerPorCodigo_cuandoExiste_retornaPromotor() {
        // Given
        when(promotoresRepository.findByCodigoPromocional("PROMO2025"))
                .thenReturn(Optional.of(promotorMock));

        // When
        Promotores resultado = promotoresService.obtenerPorCodigo("promo2025");

        // Then
        assertNotNull(resultado);
        assertEquals("PROMO2025", resultado.getCodigoPromocional());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(promotoresRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResponseStatusException.class,
                () -> promotoresService.obtenerPorId(99L));
    }
}
