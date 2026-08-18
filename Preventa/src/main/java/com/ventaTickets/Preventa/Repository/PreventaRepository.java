package com.ventaTickets.Preventa.Repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ventaTickets.Preventa.Model.Preventa;

@Repository
public interface PreventaRepository extends JpaRepository<Preventa, Long> {
    List<Preventa> findByIdEvento(Long idEvento);
    List<Preventa> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDateTime nowInicio, LocalDateTime nowFin);
}
