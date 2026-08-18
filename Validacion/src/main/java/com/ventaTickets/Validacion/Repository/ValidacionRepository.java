package com.ventaTickets.Validacion.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Validacion.Model.Validacion;

@Repository
public interface ValidacionRepository extends JpaRepository<Validacion, Long> {
    List<Validacion> findByCodigoQr(String codigoQr);
}