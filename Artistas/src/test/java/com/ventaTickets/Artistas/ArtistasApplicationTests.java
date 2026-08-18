package com.ventaTickets.Artistas;

import com.ventaTickets.Artistas.DTO.ArtistaDTO;
import com.ventaTickets.Artistas.Model.Artista;
import com.ventaTickets.Artistas.Repository.ArtistaRepository;
import com.ventaTickets.Artistas.Services.ArtistasService;
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
class ArtistasApplicationTests {

    @Mock
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private ArtistasService artistasService;

    private Artista artistaMock;

    @BeforeEach
    void setUp() {
        artistaMock = new Artista();
        artistaMock.setId(1L);
        artistaMock.setNombre("Artista Test");
        artistaMock.setGeneroMusical("Rock");
        artistaMock.setBiografia("Biografía de prueba");
        artistaMock.setEstado("ACTIVO");
        artistaMock.setFechaRegistro(LocalDateTime.now());
    }

    @Test
    void registrarArtista_dadosDTOValidos_retornaArtistaGuardado() {
        // Given
        ArtistaDTO dto = new ArtistaDTO();
        dto.setNombre("Artista Test");
        dto.setGeneroMusical("Rock");
        dto.setBiografia("Biografía de prueba");
        when(artistaRepository.save(any(Artista.class))).thenReturn(artistaMock);

        // When
        Artista resultado = artistasService.registrarArtista(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("Artista Test", resultado.getNombre());
        assertEquals("ACTIVO", resultado.getEstado());
        assertNotNull(resultado.getFechaRegistro());
        verify(artistaRepository, times(1)).save(any(Artista.class));
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaArtista() {
        // Given
        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artistaMock));

        // When
        Artista resultado = artistasService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Rock", resultado.getGeneroMusical());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(artistaRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> artistasService.obtenerPorId(99L));
        assertEquals(404, ex.getStatusCode().value());
        assertTrue(ex.getReason().contains("Artista no encontrado"));
    }

    @Test
    void obtenerTodos_retornaListaCompleta() {
        // Given
        Artista artista2 = new Artista();
        artista2.setId(2L);
        artista2.setNombre("Segundo Artista");
        artista2.setGeneroMusical("Pop");
        when(artistaRepository.findAll()).thenReturn(List.of(artistaMock, artista2));

        // When
        List<Artista> resultado = artistasService.obtenerTodos();

        // Then
        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorGenero_retornaArtistasFiltrados() {
        // Given
        when(artistaRepository.findByGeneroMusicalContainingIgnoreCase("rock"))
                .thenReturn(List.of(artistaMock));

        // When
        List<Artista> resultado = artistasService.buscarPorGenero("rock");

        // Then
        assertEquals(1, resultado.size());
        assertEquals("Rock", resultado.get(0).getGeneroMusical());
    }

    @Test
    void actualizarArtista_cuandoExiste_retornaArtistaActualizado() {
        // Given
        ArtistaDTO dto = new ArtistaDTO();
        dto.setNombre("Nuevo Nombre");
        dto.setGeneroMusical("Jazz");
        dto.setBiografia("Nueva bio");

        Artista actualizado = new Artista();
        actualizado.setId(1L);
        actualizado.setNombre("Nuevo Nombre");
        actualizado.setGeneroMusical("Jazz");

        when(artistaRepository.findById(1L)).thenReturn(Optional.of(artistaMock));
        when(artistaRepository.save(any(Artista.class))).thenReturn(actualizado);

        // When
        Artista resultado = artistasService.actualizarArtista(1L, dto);

        // Then
        assertEquals("Nuevo Nombre", resultado.getNombre());
        assertEquals("Jazz", resultado.getGeneroMusical());
    }
}
