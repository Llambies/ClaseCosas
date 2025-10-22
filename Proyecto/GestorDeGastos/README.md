# Gestor de Gastos - PostgreSQL + pgAdmin

Sistema de gestión de gastos personales con PostgreSQL y pgAdmin en Docker.

## 📋 Requisitos

- Docker
- Docker Compose

## 🚀 Inicio Rápido

### 1. Levantar los servicios

```bash
docker-compose up -d
```

### 2. Verificar que los contenedores están corriendo

```bash
docker-compose ps
```

## 🔧 Acceso a los Servicios

### PostgreSQL
- **Host:** localhost
- **Puerto:** 5433 (externo) / 5432 (interno en Docker)
- **Usuario:** admin
- **Contraseña:** admin123
- **Base de datos:** gestor_gastos

### pgAdmin
- **URL:** http://localhost:5050
- **Email:** admin@admin.com
- **Contraseña:** admin123

## 📊 Configurar pgAdmin

1. Abre http://localhost:5050 en tu navegador
2. Inicia sesión con las credenciales de pgAdmin
3. Haz clic en "Add New Server"
4. En la pestaña "General":
   - **Name:** Gestor Gastos (o el nombre que prefieras)
5. En la pestaña "Connection":
   - **Host:** postgres (nombre del servicio en Docker)
   - **Port:** 5432
   - **Maintenance database:** gestor_gastos
   - **Username:** admin
   - **Password:** admin123
6. Haz clic en "Save"

## 📁 Estructura de la Base de Datos

### Tabla: Pagos

| Campo     | Tipo          | Descripción                    |
|-----------|---------------|--------------------------------|
| id        | SERIAL        | Identificador único (PK)       |
| nombre    | VARCHAR(255)  | Descripción del pago           |
| categoria | VARCHAR(100)  | Categoría del gasto            |
| cantidad  | DECIMAL(10,2) | Cantidad del pago              |
| cuando    | TIMESTAMP     | Fecha y hora del pago          |

### Datos de Ejemplo

La base de datos se inicializa con algunos registros de ejemplo:
- Compra supermercado (Alimentación)
- Gasolina (Transporte)
- Cena restaurante (Ocio)
- Factura luz (Servicios)
- Netflix (Entretenimiento)

## 🛠️ Comandos Útiles

### Detener los servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes (⚠️ elimina todos los datos)
```bash
docker-compose down -v
```

### Ver logs
```bash
docker-compose logs -f
```

### Ver logs de un servicio específico
```bash
docker-compose logs -f postgres
docker-compose logs -f pgadmin
```

### Reiniciar los servicios
```bash
docker-compose restart
```

### Conectar a PostgreSQL desde la línea de comandos
```bash
docker exec -it gestor_gastos_db psql -U admin -d gestor_gastos
```

## 📝 Consultas SQL de Ejemplo

### Ver todos los pagos
```sql
SELECT * FROM Pagos ORDER BY cuando DESC;
```

### Insertar un nuevo pago
```sql
INSERT INTO Pagos (nombre, categoria, cantidad, cuando) 
VALUES ('Café', 'Alimentación', 2.50, NOW());
```

### Total de gastos por categoría
```sql
SELECT categoria, SUM(cantidad) as total 
FROM Pagos 
GROUP BY categoria 
ORDER BY total DESC;
```

### Gastos del mes actual
```sql
SELECT * FROM Pagos 
WHERE EXTRACT(MONTH FROM cuando) = EXTRACT(MONTH FROM CURRENT_DATE)
  AND EXTRACT(YEAR FROM cuando) = EXTRACT(YEAR FROM CURRENT_DATE)
ORDER BY cuando DESC;
```

## 🔒 Seguridad

⚠️ **IMPORTANTE:** Las credenciales por defecto son solo para desarrollo. Para producción:

1. Cambia las credenciales en el archivo `.env`
2. Usa contraseñas seguras
3. No subas el archivo `.env` a repositorios públicos

## 📦 Volúmenes

Los datos se persisten en volúmenes de Docker:
- `postgres_data`: Datos de PostgreSQL
- `pgadmin_data`: Configuración de pgAdmin

Estos volúmenes aseguran que tus datos no se pierdan al detener los contenedores.
