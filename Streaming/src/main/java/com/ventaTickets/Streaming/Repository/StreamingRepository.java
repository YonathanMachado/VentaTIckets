package com.ventaTickets.Streaming.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventaTickets.Streaming.Model.Streaming;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Long> {
    Optional<Streaming> findByIdEvento(Long idEvento);
}