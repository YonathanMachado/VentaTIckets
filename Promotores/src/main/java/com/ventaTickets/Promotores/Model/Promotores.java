package com.ventaTickets.Promotores.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promotores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promotores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    @Column(unique = true)
    private String email;
    
    @Column(name = "codigo_promocional", unique = true)
    private String codigoPromocional;
    
    @Column(name = "descuento_porcentaje")
    private Double descuentoPorcentaje;
    
    @Column(name = "comision_porcentaje")
    private Double comisionPorcentaje;
    
    private String estado;

    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

}
