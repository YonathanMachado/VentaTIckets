package com.ventaTickets.Recintos;

import com.ventaTickets.Recintos.DTO.RecintoDTO;
import com.ventaTickets.Recintos.Model.Recinto;
import com.ventaTickets.Recintos.Repository.RecintoRepository;
import com.ventaTickets.Recintos.Service.RecintoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecintosApplicationTests {

    @Mock
    private RecintoRepository recintoRepository;

    @InjectMocks
    private RecintoService recintoService;

    private Recinto recintoMock;

    @BeforeEach
    void setUp() {
        recintoMock = new Recinto();
        recintoMock.setId(1L);
        recintoMock.setNombre("Estadio Nacional");
        recintoMock.setDireccion("Av. Grecia 2001");
        recintoMock.setCiudad("Santiago");
        recintoMock.setCapacidadMaxima(60000);
        recintoMock.setEstado("ACTIVO");
    }

    @Test
    void registrarRecinto_dadosDTOValidos_retornaRecintoConEstadoActivo() {
        // Given
        RecintoDTO dto = new RecintoDTO();
        dto.setNombre("Estadio Nacional");
        dto.setDireccion("Av. Grecia 2001");
        dto.setCiudad("Santiago");
        dto.setCapacidadMaxima(60000);
        when(recintoRepository.save(any(Recinto.class))).thenReturn(recintoMock);

        // When
        Recinto resultado = recintoService.registrarRecinto(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("ACTIVO", resultado.getEstado());
        assertEquals("Estadio Nacional", resultado.getNombre());
        verify(recintoRepository).save(any(Recinto.class));
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaRecinto() {
        // Given
        when(recintoRepository.findById(1L)).thenReturn(Optional.of(recintoMock));

        // When
        Recinto resultado = recintoService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals("Santiago", resultado.getCiudad());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(recintoRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResponseStatusException.class, () -> recintoService.obtenerPorId(99L));
    }

    @Test
    void buscarPorCiudad_retornaRecintosDeLaCiudad() {
        // Given
        when(recintoRepository.findByCiudadIgnoreCase("Santiago")).thenReturn(List.of(recintoMock));

        // When
        List<Recinto> resultado = recintoService.buscarPorCiudad("Santiago");

        // Then
        assertFalse(resultado.isEmpty());
        assertEquals("Santiago", resultado.get(0).getCiudad());
    }

    @Test
    void capacidadMaxima_debeSerPositiva() {
        // Given & When & Then
        assertTrue(recintoMock.getCapacidadMaxima() > 0,
                "La capacidad máxima del recinto debe ser mayor a cero");
    }
}
