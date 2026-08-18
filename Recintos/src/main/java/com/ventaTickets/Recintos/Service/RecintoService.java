package com.ventaTickets.Recintos.Service;

import com.ventaTickets.Recintos.DTO.RecintoDTO;
import com.ventaTickets.Recintos.Model.Recinto;
import com.ventaTickets.Recintos.Repository.RecintoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecintoService {

    @Autowired
    private RecintoRepository recintoRepository;

    public Recinto registrarRecinto(RecintoDTO dto) {
        Recinto recinto = new Recinto();
        recinto.setNombre(dto.getNombre());
        recinto.setDireccion(dto.getDireccion());
        recinto.setCiudad(dto.getCiudad());
        recinto.setCapacidadMaxima(dto.getCapacidadMaxima());
        recinto.setEstado("ACTIVO");

        return recintoRepository.save(recinto);
    }

    public List<Recinto> obtenerTodos() {
        return recintoRepository.findAll();
    }

    public Recinto obtenerPorId(Long id) {
        return recintoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recinto no encontrado con ID: " + id));
    }

    public List<Recinto> buscarPorCiudad(String ciudad) {
        return recintoRepository.findByCiudadContainingIgnoreCase(ciudad);
    }

    public List<Recinto> obtenerTodosActivos() {
        return recintoRepository.findByEstadoIgnoreCase("ACTIVO");
    }

    public Recinto obtenerRecintoActivoPorId(Long id) {
        Recinto recinto = recintoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recinto no encontrado con ID: " + id));

        if (!"ACTIVO".equalsIgnoreCase(recinto.getEstado())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recinto no disponible" );
        }

        return recinto;
    }

    public List<Recinto> buscarPorCiudadActiva(String ciudad) {
        return recintoRepository.findByCiudadContainingIgnoreCaseAndEstadoIgnoreCase(ciudad, "ACTIVO");
    }

    public Integer obtenerCapacidad(Long id) {
        Recinto recinto = obtenerRecintoActivoPorId(id);
        return recinto.getCapacidadMaxima();
    }

    public Recinto actualizarRecinto(Long id, RecintoDTO dto) {
        Recinto recinto = recintoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recinto no encontrado con ID: " + id));

        recinto.setNombre(dto.getNombre());
        recinto.setDireccion(dto.getDireccion());
        recinto.setCiudad(dto.getCiudad());
        recinto.setCapacidadMaxima(dto.getCapacidadMaxima());

        return recintoRepository.save(recinto);
    }

    public Recinto actualizarEstado(Long id, String estado) {
        Recinto recinto = recintoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recinto no encontrado con ID: " + id));

        String estadoNormalizado = estado.toUpperCase();

        if (!"ACTIVO".equals(estadoNormalizado) && !"INACTIVO".equals(estadoNormalizado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVO o INACTIVO.");
        }

        recinto.setEstado(estadoNormalizado);
        return recintoRepository.save(recinto);
    }
}

