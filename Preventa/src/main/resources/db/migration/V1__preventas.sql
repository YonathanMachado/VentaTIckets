-- MySQL-compatible migration for table `preventas`
CREATE TABLE IF NOT EXISTS preventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_evento BIGINT NOT NULL,
    nombre VARCHAR(255),
    precio DECIMAL(19,2) NOT NULL,
    fecha_inicio DATETIME,
    fecha_fin DATETIME,
    limite_tickets INT,
    tickets_vendidos INT,
    estado VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;