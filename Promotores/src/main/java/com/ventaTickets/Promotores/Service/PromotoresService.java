package com.ventaTickets.Promotores.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ventaTickets.Promotores.DTO.PromotoresDTO;
import com.ventaTickets.Promotores.Model.Promotores;
import com.ventaTickets.Promotores.Repository.PromotoresRepository;

@Service
public class PromotoresService {

    @Autowired
    private PromotoresRepository promotoresRepository;

    public Promotores registrarPromotor(PromotoresDTO dto) {
        if (promotoresRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado.");
        }

        if (promotoresRepository.existsByCodigoPromocional(dto.getCodigoPromocional())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El código promocional ya existe.");
        }

        Promotores promotores = new Promotores();
        promotores.setNombre(dto.getNombre());
        promotores.setEmail(dto.getEmail());
        promotores.setCodigoPromocional(dto.getCodigoPromocional().toUpperCase());
        promotores.setDescuentoPorcentaje(dto.getDescuentoPorcentaje());
        promotores.setComisionPorcentaje(dto.getComisionPorcentaje());
        promotores.setEstado("ACTIVO");

        return promotoresRepository.save(promotores);
    }

    public List<Promotores> obtenerTodos() {
        return promotoresRepository.findAll().stream()
                .filter(promotor -> "ACTIVO".equalsIgnoreCase(promotor.getEstado()))
                .toList();
    }

    public Promotores obtenerPorId(Long id) {
        return promotoresRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotor no encontrado."));
    }

    public Promotores obtenerPorCodigo(String codigo) {
        return promotoresRepository.findByCodigoPromocional(codigo.toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Código promocional inválido o no encontrado."));
    }

    public Map<String, Object> obtenerRendimiento(Long id) {
        Promotores promotor = obtenerPorId(id);
        return Map.of(
                "promotorId", promotor.getId(),
                "codigoPromocional", promotor.getCodigoPromocional(),
                "estado", promotor.getEstado(),
                "ticketsVendidos", 0
        );
    }

    public Promotores actualizarPromotor(Long id, PromotoresDTO dto) {
        Promotores promotor = promotoresRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotor no encontrado."));

        // Validar que si cambia el email, el nuevo no esté ocupado por OTRO promotor
        if (!promotor.getEmail().equalsIgnoreCase(dto.getEmail()) && promotoresRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado por otro promotor.");
        }

        // Validar que si cambia el código, el nuevo no esté ocupado por OTRO promotor
        if (!promotor.getCodigoPromocional().equalsIgnoreCase(dto.getCodigoPromocional()) 
            && promotoresRepository.existsByCodigoPromocional(dto.getCodigoPromocional().toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El código promocional ya existe.");
        }
        
        promotor.setNombre(dto.getNombre());
        promotor.setEmail(dto.getEmail());
        promotor.setCodigoPromocional(dto.getCodigoPromocional().toUpperCase());
        promotor.setDescuentoPorcentaje(dto.getDescuentoPorcentaje());
        promotor.setComisionPorcentaje(dto.getComisionPorcentaje());

        return promotoresRepository.save(promotor);
    }

    public Promotores actualizarEstadoPromotor(Long id, String estado) {
        Promotores promotor = promotoresRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promotor no encontrado."));

        String estadoNormalizado = estado.toUpperCase();

        if (!"ACTIVO".equals(estadoNormalizado) && !"INACTIVO".equals(estadoNormalizado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVO o INACTIVO.");
        }

        promotor.setEstado(estadoNormalizado);
        return promotoresRepository.save(promotor);
    }
}
