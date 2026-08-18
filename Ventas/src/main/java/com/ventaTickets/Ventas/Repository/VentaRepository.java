package com.ventaTickets.Ventas.Repository;

import com.ventaTickets.Ventas.Model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByIdUsuario(Long idUsuario);
    List<Venta> findByIdEvento(Long idEvento);
}