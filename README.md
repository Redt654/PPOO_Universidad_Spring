# API Gestión Académica (PPOO)

Por ahora solo está implementado los módulos de profesores (`/api/v1/teachers`), estudiantes (`/api/v1/students`), y carreras (`/api/v1/careers`).

---

## CIPA
- Victor Manuel Rodriguez
- Juan Dueñas Garcia
- David Mauricio Bustos Montoya
- Juan Sebastian Tijaro Morales

---

## Docker
Para levantar la aplicación y la base de datos por primera vez:
```bash
docker compose up -d
```
Para iniciar o detener los contenedores:
```bash
docker compose start
docker compose stop
```

## Stack
- Java 21 / Spring Boot 4
- PostgreSQL + Spring Data JPA
- Gradle
- Swagger / Scalar
- Postman (colección disponible en postman_collection/PPOO_Universidad.postman_collection.json)

## Documentación
- Swagger UI: /swagger-ui/index.html
- Scalar: /scalar

## Incompleto
- Diseñar e implementar los casos de uso y relaciones para las entidades Course, CourseStudent y Subject.
- Implementar autenticación y pruebas unitarias.
- Cubrir más casos de error.
