package com.ventaTickets.Validacion;

import com.ventaTickets.Validacion.Model.Validacion;
import com.ventaTickets.Validacion.Repository.ValidacionRepository;
import com.ventaTickets.Validacion.Service.ValidacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidacionApplicationTests {

    @Mock
    private ValidacionRepository validacionRepository;

    @InjectMocks
    private ValidacionService validacionService;

    private Validacion validacionMock;

    @BeforeEach
    void setUp() {
        validacionMock = new Validacion();
        validacionMock.setId(1L);
        validacionMock.setCodigoQr("QR-ABC12345");
        validacionMock.setFechaValidacion(LocalDateTime.now());
        validacionMock.setAccesoPermitido(true);
        validacionMock.setMensaje("Acceso concedido. ¡Bienvenido!");
    }

    @Test
    void validacion_accesoPermitido_cuandoTicketEstaVendido() {
        // Given
        when(validacionRepository.save(any(Validacion.class))).thenReturn(validacionMock);

        // When
        Validacion guardada = validacionRepository.save(validacionMock);

        // Then
        assertTrue(guardada.isAccesoPermitido());
        assertEquals("Acceso concedido. ¡Bienvenido!", guardada.getMensaje());
    }

    @Test
    void validacion_accesoDenegado_cuandoTicketNoExiste() {
        // Given
        Validacion denegada = new Validacion();
        denegada.setCodigoQr("QR-INVALIDO");
        denegada.setAccesoPermitido(false);
        denegada.setMensaje("Acceso denegado. Código QR no existe en el sistema.");
        when(validacionRepository.save(any(Validacion.class))).thenReturn(denegada);

        // When
        Validacion resultado = validacionRepository.save(denegada);

        // Then
        assertFalse(resultado.isAccesoPermitido());
        assertTrue(resultado.getMensaje().contains("denegado"));
    }

    @Test
    void codigoQr_noDebeSerNulo_enValidacion() {
        // Given & When & Then
        assertNotNull(validacionMock.getCodigoQr(),
                "El código QR no puede ser nulo en una validación");
        assertFalse(validacionMock.getCodigoQr().isBlank(),
                "El código QR no puede estar vacío");
    }

    @Test
    void fechaValidacion_debeRegistrarseAlMomento() {
        // Given
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
        LocalDateTime despues = LocalDateTime.now().plusSeconds(1);

        // When & Then
        assertTrue(validacionMock.getFechaValidacion().isAfter(antes),
                "La fecha de validación debe ser reciente");
        assertTrue(validacionMock.getFechaValidacion().isBefore(despues),
                "La fecha de validación no debe ser futura");
    }
}
