package com.ventaTickets.Tickets.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Tickets.Model.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByIdEvento(Long idEvento);
    List<Ticket> findByIdUsuario(Long idUsuario);
    Optional<Ticket> findByCodigoQr(String codigoQr);
    List<Ticket> findByIdEventoAndEstado(Long idEvento, String estado);
}
