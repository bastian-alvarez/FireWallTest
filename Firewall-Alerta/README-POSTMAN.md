# Microservicio Firewall Alerta

## Correr local sin Docker

```bash
mvnw.cmd spring-boot:run
```

Endpoint:

```http
POST http://localhost:8082/alerts
Content-Type: application/json
```

Body para Postman:

```json
{
  "type": "Forest Fire",
  "location": "Santiago, Chile",
  "severity": "High",
  "description": "Alerta generada desde Postman"
}
```

Respuesta esperada: `201 Created` con un JSON parecido a:

```json
{
  "id": 1,
  "timestamp": "2026-04-28T20:30:00",
  "type": "Forest Fire",
  "location": "Santiago, Chile",
  "severity": "High",
  "description": "Alerta generada desde Postman"
}
```

También puedes probar:

```http
GET http://localhost:8082/alerts
GET http://localhost:8082/alerts/1
```

## Correr con Docker Compose

```bash
docker compose up --build
```

La API queda en:

```http
http://localhost:8082/alerts
```

MySQL queda expuesto para Workbench en:

- Host: `localhost`
- Puerto: `3307`
- Usuario: `alert_user`
- Contraseña: `alert_pass`
- Base de datos: `firewall_alerta`
