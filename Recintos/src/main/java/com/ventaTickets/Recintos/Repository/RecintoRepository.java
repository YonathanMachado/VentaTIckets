package com.ventaTickets.Recintos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Recintos.Model.Recinto;

import java.util.List;

@Repository
public interface RecintoRepository extends JpaRepository<Recinto, Long> {
    List<Recinto> findByCiudadIgnoreCase(String ciudad);
    List<Recinto> findByCiudadContainingIgnoreCase(String ciudad);
    List<Recinto> findByEstadoIgnoreCase(String estado);
    List<Recinto> findByCiudadContainingIgnoreCaseAndEstadoIgnoreCase(String ciudad, String estado);
}
