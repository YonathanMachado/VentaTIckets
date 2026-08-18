package com.ventaTickets.Promotores.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Promotores.Model.Promotores;

@Repository
public interface PromotoresRepository  extends JpaRepository<Promotores, Long> {
    Optional<Promotores> findByCodigoPromocional(String codigoPromocional);
    boolean existsByEmail(String email);
    boolean existsByCodigoPromocional(String codigoPromocional);
}
