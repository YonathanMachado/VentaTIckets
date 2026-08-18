# VentaTIckets

Sistema de venta de entradas para eventos, implementado como una arquitectura de microservicios con Java Spring Boot. Proyecto académico de la carrera de Ingeniería en Informática (DuocUC).

## Arquitectura

El sistema está compuesto por 10 microservicios independientes, cada uno con su propia base de datos MySQL, más un API Gateway que centraliza el acceso.

| Microservicio | Puerto | Descripción | Depende de |
|---|---|---|---|
| **mc-api-gateway** | 8080 | Gateway de entrada, enruta las peticiones a cada microservicio | Todos los servicios |
| **Eventos** | 8081 | Gestión de eventos | — |
| **Recintos** | 8082 | Gestión de recintos/venues | — |
| **Tickets** | 8083 | Gestión de tickets | Eventos |
| **Ventas** | 8084 | Gestión de ventas | Tickets |
| **Validacion** | 8085 | Validación de tickets | Tickets |
| **Artistas** | 8086 | Gestión de artistas | — |
| **Streaming** | 8087 | Streaming de eventos | Eventos |
| **Devoluciones** | 8088 | Gestión de devoluciones | Tickets |
| **Promotores** | 8089 | Gestión de promotores | — |
| **Preventa** | 8090 | Gestión de preventas | Eventos |

Cada microservicio corre en su propia base de datos MySQL (`db_eventos`, `db_recintos`, `db_tickets`, etc.), creada automáticamente al levantar el contenedor.

## Stack tecnológico

- **Java 21**
- **Spring Boot** (Spring Data JPA, Spring Cloud Gateway)
- **MySQL 8.0**
- **Docker / Docker Compose**
- **Maven**
- **Observabilidad** (microservicio Artistas): Prometheus, Pushgateway, Grafana, Loki/Promtail

## Requisitos previos

- Docker y Docker Compose
- Java 21 (para desarrollo/build local)
- Maven (opcional, cada servicio incluye Maven Wrapper `mvnw`)

## Cómo levantar el proyecto

Desde la raíz del repositorio:

```bash
docker-compose up --build
```

Esto levanta MySQL y los 10 microservicios, junto con el API Gateway en `http://localhost:8080`. Las peticiones al Gateway se enrutan automáticamente a cada microservicio según la ruta configurada.

### Stack de observabilidad (opcional)

El microservicio **Artistas** incluye su propio `docker-compose.yml` con Prometheus, Pushgateway y Grafana:

```bash
cd Artistas
docker-compose up --build
```

- Prometheus: `http://localhost:9090`
- Pushgateway: `http://localhost:9091`
- Grafana: `http://localhost:3000`

## Estructura del repositorio

```
ventaTickets-main/
├── mc-api-gateway/       # API Gateway (Spring Cloud Gateway)
├── Eventos/
├── Recintos/
├── Tickets/
├── Ventas/
├── Validacion/
├── Artistas/             # incluye stack de observabilidad (Prometheus/Grafana/Loki)
├── Streaming/
├── Devoluciones/
├── Promotores/
├── Preventa/
└── docker-compose.yml    # orquesta todos los servicios + MySQL
```

Cada microservicio sigue la estructura estándar de un proyecto Spring Boot (Maven), con su propio `pom.xml`, `Dockerfile` y `src/`.

## Desarrollo local (sin Docker)

Dentro de cada carpeta de microservicio:

```bash
./mvnw spring-boot:run
```

Ten en cuenta que cada servicio requiere una instancia de MySQL accesible y las variables de entorno correspondientes (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`), tal como están definidas en el `docker-compose.yml` raíz.
