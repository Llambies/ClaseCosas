# API Frases Celebres con Spring Boot

API REST para gestionar frases celebres clasificadas por autor y categoria.

## Requisitos

- Java 21
- Gradle (se incluye wrapper: `./gradlew`)
- PostgreSQL 14+ (recomendado)

## Configuracion de base de datos

Por defecto el proyecto usa:

- URL: `jdbc:postgresql://localhost:5432/frasesdb`
- Usuario: `postgres`
- Password: `postgres`

Si necesitas cambiarlo, edita `src/main/resources/application.properties`.

## Puesta en marcha

1. Crear base de datos:

```sql
CREATE DATABASE frasesdb;
```

1. Ejecutar la aplicacion:

```bash
./gradlew bootRun
```

1. Verificar Swagger:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

## Seguridad (Spring Security)

Se usa autenticacion HTTP Basic con dos roles:

- `ADMIN`: puede hacer CRUD completo.
- `STANDARD`: solo endpoints `GET`.

Usuarios iniciales (cargados por `data.sql`):

- Usuario ADMIN: `admin` / `password`
- Usuario STANDARD: `standard` / `password`

## Endpoints principales

Prefijo global: `/api/v1`

- `GET /api/v1/autores`
- `GET /api/v1/autores/{id}`
- `GET /api/v1/autores/{id}/frases`
- `POST|PUT|DELETE /api/v1/autores`

- `GET /api/v1/categorias`
- `GET /api/v1/categorias/{id}`
- `GET /api/v1/categorias/{id}/frases`
- `POST|PUT|DELETE /api/v1/categorias`

- `GET /api/v1/frases`
- `GET /api/v1/frases/{id}`
- `GET /api/v1/frases/dia`
- `GET /api/v1/frases/dia?fecha=yyyy-MM-dd`
- `POST|PUT|DELETE /api/v1/frases`

Paginacion soportada con `page` y `size`.

## Ejemplos de llamadas

Obtener frases:

```bash
curl -u standard:password "http://localhost:8080/api/v1/frases?page=0&size=10"
```

Obtener frase del dia:

```bash
curl -u standard:password "http://localhost:8080/api/v1/frases/dia"
```

Crear categoria (solo ADMIN):

```bash
curl -u admin:password -X POST "http://localhost:8080/api/v1/categorias" \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Historia"}'
```

## Capturas de pantalla

Incluye en la entrega final al menos 3 capturas con ejemplos de llamadas, por ejemplo:

1. `GET /api/v1/frases/dia` en Swagger
2. `GET /api/v1/autores?page=0&size=10`
3. `POST /api/v1/categorias` autenticado como ADMIN
