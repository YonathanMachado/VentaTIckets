package com.ventaTickets.Devoluciones.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Devoluciones.Model.Devoluciones;

@Repository
public interface DevolucionesRepository extends JpaRepository<Devoluciones, Long> {
    List<Devoluciones> findByIdTicket(Long idTicket);
}
