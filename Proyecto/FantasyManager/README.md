# Fantasy Manager

Sistema de gestión de ligas de fantasía con criaturas, equipos, subastas y simulaciones de combate.

## 📋 Descripción

Fantasy Manager es una aplicación multiplataforma desarrollada como proyecto intermodular que permite a los usuarios:
- Crear y unirse a ligas (públicas o privadas)
- Gestionar equipos de criaturas personalizables
- Participar en subastas para adquirir nuevas criaturas
- Ver simulaciones de combates diarios
- Competir en rankings y clasificaciones

## 🏗️ Arquitectura

El proyecto está dividido en tres componentes principales:

### Backend (Node.js + Express + MongoDB)
- API REST con autenticación JWT
- Gestión de usuarios, ligas, criaturas, equipos, subastas y combates
- Motor de simulación de combates
- Base de datos MongoDB

### Frontend (Flutter)
- Aplicación multiplataforma (móvil, web, escritorio)
- Gestión de estado con Provider
- Interfaz adaptativa y responsiva

### Base de Datos (MongoDB)
- Contenedor Docker para desarrollo local

## 🚀 Instalación y Configuración

### Prerrequisitos
- Node.js (v18 o superior)
- Flutter SDK (v3.0 o superior)
- Docker y Docker Compose
- MongoDB (o usar el contenedor Docker)

### Backend

1. Navegar al directorio del backend:
```bash
cd BackEnd
```

2. Instalar dependencias:
```bash
npm install
```

3. Crear archivo `.env`:
```env
PORT=3000
MONGODB_URI=mongodb://admin:admin123@localhost:27017/fantasy_manager?authSource=admin
JWT_SECRET=tu_secreto_jwt_aqui_cambiar_en_produccion
JWT_EXPIRES_IN=7d
NODE_ENV=development
```

4. Iniciar MongoDB con Docker:
```bash
cd ../BaseDatos
docker-compose up -d
```

5. Iniciar el servidor:
```bash
cd ../BackEnd
npm run dev
```

El servidor estará disponible en `http://localhost:3000`

### Frontend

1. Navegar al directorio del frontend:
```bash
cd FrontEnd
```

2. Instalar dependencias:
```bash
flutter pub get
```

3. Configurar la URL de la API en `lib/config/api_config.dart`:
```dart
static const String baseUrl = 'http://localhost:3000';
```

**Nota:** Para dispositivos móviles físicos, cambiar `localhost` por la IP de tu máquina local.

4. Ejecutar la aplicación:
```bash
# Móvil
flutter run

# Web
flutter run -d chrome

# Escritorio
flutter run -d linux  # o windows/macos
```

## 📁 Estructura del Proyecto

```
FantasyManager/
├── BackEnd/
│   ├── src/
│   │   ├── config/          # Configuración de BD
│   │   ├── controllers/      # Controladores de rutas
│   │   ├── middleware/       # Middlewares (auth, etc.)
│   │   ├── models/          # Modelos de Mongoose
│   │   ├── routes/          # Definición de rutas
│   │   ├── services/        # Servicios (simulación, etc.)
│   │   ├── utils/           # Utilidades (JWT, etc.)
│   │   ├── app.js           # Configuración Express
│   │   └── index.js         # Punto de entrada
│   └── package.json
│
├── FrontEnd/
│   ├── lib/
│   │   ├── config/          # Configuración API
│   │   ├── models/          # Modelos de datos
│   │   ├── providers/      # Gestión de estado
│   │   ├── screens/        # Pantallas
│   │   ├── services/       # Servicios API
│   │   └── main.dart       # Punto de entrada
│   └── pubspec.yaml
│
├── BaseDatos/
│   └── docker-compose.yml   # Configuración MongoDB
│
└── Documentacion/          # Documentación del proyecto
```

## 🔌 Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registro de usuario
- `POST /api/auth/login` - Inicio de sesión
- `GET /api/auth/profile` - Perfil del usuario (requiere auth)

### Ligas
- `GET /api/leagues` - Listar ligas
- `POST /api/leagues` - Crear liga (requiere auth)
- `GET /api/leagues/:id` - Obtener liga por ID
- `POST /api/leagues/join` - Unirse a liga (requiere auth)

### Criaturas
- `GET /api/creatures` - Listar criaturas
- `GET /api/creatures/:id` - Obtener criatura por ID
- `POST /api/creatures` - Crear criatura (requiere auth)

### Equipos
- `GET /api/teams/league/:leagueId` - Obtener equipo del usuario
- `PUT /api/teams/league/:leagueId` - Actualizar equipo (requiere auth)

### Subastas
- `GET /api/auctions` - Listar subastas activas
- `POST /api/auctions/:auctionId/bid` - Realizar puja (requiere auth)

### Combates
- `GET /api/battles` - Listar combates
- `POST /api/battles/league/:leagueId/simulate` - Ejecutar simulación (requiere auth)

## 🎮 Funcionalidades Principales

### Gestión de Usuarios
- Registro e inicio de sesión seguro
- Perfil de usuario con presupuesto y puntos
- Autenticación JWT

### Gestión de Ligas
- Crear ligas públicas o privadas
- Códigos de invitación para ligas privadas
- Límite de participantes configurable

### Gestión de Criaturas
- Base de datos de criaturas con estadísticas base
- Movimientos y tipos
- Precio base para subastas

### Gestión de Equipos
- Hasta 6 criaturas por equipo
- Configuración de EVs (hasta 510 puntos totales)
- Selección de movimientos
- Validación de reglas

### Sistema de Subastas
- Subastas con tiempo límite
- Sistema de pujas
- Asignación automática al ganador

### Simulación de Combates
- Motor de simulación básico
- Cálculo de daño y resultados
- Sistema de puntuación
- Logs de combate turno a turno

## 🔒 Seguridad

- Contraseñas hasheadas con bcrypt
- Autenticación JWT
- Validación de datos en servidor
- Middleware de autenticación para rutas protegidas

## 📝 Notas de Desarrollo

- El motor de simulación es una versión básica y puede mejorarse
- Las validaciones de reglas de juego están implementadas en el backend
- El frontend está preparado para diseño adaptativo (móvil/escritorio)
- Se recomienda usar variables de entorno para configuración sensible

## 🐛 Solución de Problemas

### Error de conexión a MongoDB
- Verificar que Docker esté corriendo
- Verificar que el contenedor MongoDB esté activo: `docker ps`
- Verificar las credenciales en `.env`

### Error de CORS en el frontend
- Verificar que el backend tenga CORS habilitado
- Verificar que la URL de la API sea correcta

### Error de autenticación
- Verificar que el token JWT sea válido
- Verificar que el JWT_SECRET sea el mismo en desarrollo

## 📚 Documentación Adicional

Ver la carpeta `Documentacion/` para más detalles sobre:
- Requerimientos funcionales y no funcionales
- Casos de uso
- Diagramas de arquitectura
- Planificación temporal

## 👥 Contribución

Este es un proyecto académico desarrollado como proyecto intermodular.

## 📄 Licencia

ISC
