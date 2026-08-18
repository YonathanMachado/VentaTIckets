package com.ventaTickets.Tickets;

import com.ventaTickets.Tickets.DTO.TicketDTO;
import com.ventaTickets.Tickets.Model.Ticket;
import com.ventaTickets.Tickets.Repository.TicketRepository;
import com.ventaTickets.Tickets.Service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketsApplicationTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticketMock;

    @BeforeEach
    void setUp() {
        ticketMock = new Ticket();
        ticketMock.setId(1L);
        ticketMock.setIdEvento(10L);
        ticketMock.setIdUsuario(5L);
        ticketMock.setPrecio(50000.0);
        ticketMock.setEstado("VENDIDO");
        ticketMock.setCodigoQr("QR-ABC12345");
        ticketMock.setFechaCompra(LocalDateTime.now());
    }

    // ===================== obtenerPorId =====================

    @Test
    void obtenerPorId_cuandoExiste_retornaTicket() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticketMock));

        // When
        Ticket resultado = ticketService.obtenerPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("VENDIDO", resultado.getEstado());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaNotFoundException() {
        // Given
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> ticketService.obtenerPorId(99L));
        assertEquals(404, ex.getStatusCode().value());
    }

    // ===================== buscarPorUsuario =====================

    @Test
    void buscarPorUsuario_retornaListaDeTickets() {
        // Given
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setIdUsuario(5L);
        ticket2.setEstado("VENDIDO");
        when(ticketRepository.findByIdUsuario(5L)).thenReturn(List.of(ticketMock, ticket2));

        // When
        List<Ticket> resultado = ticketService.buscarPorUsuario(5L);

        // Then
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(t -> t.getIdUsuario() == 5L));
    }

    @Test
    void buscarPorUsuario_sinTickets_retornaListaVacia() {
        // Given
        when(ticketRepository.findByIdUsuario(99L)).thenReturn(List.of());

        // When
        List<Ticket> resultado = ticketService.buscarPorUsuario(99L);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
