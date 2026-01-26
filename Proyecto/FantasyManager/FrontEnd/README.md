# Fantasy Manager - Frontend Flutter

Aplicación multiplataforma desarrollada con Flutter para dispositivos móviles, escritorio y web.

## 🚀 Requisitos Previos

- Flutter SDK (versión 3.0.0 o superior)
- Dart SDK
- Un editor de código (VS Code, Android Studio, etc.)

## 📦 Instalación

1. Instalar dependencias:
```bash
flutter pub get
```

2. Verificar que Flutter esté correctamente configurado:
```bash
flutter doctor
```

## 🏃 Ejecutar la Aplicación

### Móvil (Android/iOS)
```bash
flutter run
```

### Web
```bash
flutter run -d chrome
# o
flutter run -d web-server
```

### Escritorio (Linux/Windows/macOS)
```bash
flutter run -d linux
# o
flutter run -d windows
# o
flutter run -d macos
```

## 🏗️ Estructura del Proyecto

```
lib/
├── config/
│   └── api_config.dart          # Configuración de la API
├── models/
│   └── example_model.dart       # Modelos de datos
├── services/
│   ├── api_service.dart         # Servicio HTTP genérico
│   └── example_service.dart     # Servicio específico de ejemplos
├── providers/
│   └── example_provider.dart    # Gestión de estado con Provider
├── screens/
│   ├── home_screen.dart         # Pantalla principal
│   └── example_form_screen.dart # Formulario de ejemplo
└── main.dart                     # Punto de entrada
```

## ⚙️ Configuración

Antes de ejecutar la aplicación, asegúrate de:

1. **Backend corriendo**: El servidor Node.js debe estar ejecutándose en `http://localhost:3000`

2. **MongoDB corriendo**: La base de datos debe estar disponible (usando docker-compose)

3. **Configurar la URL de la API**: Si necesitas cambiar la URL del backend, edita `lib/config/api_config.dart`

## 📱 Características

- ✅ Arquitectura limpia con separación de responsabilidades
- ✅ Gestión de estado con Provider
- ✅ Servicios HTTP reutilizables
- ✅ Soporte multiplataforma (móvil, web, escritorio)
- ✅ Manejo de errores y estados de carga
- ✅ Interfaz moderna con Material Design 3

## 🔧 Desarrollo

Para ejecutar en modo desarrollo con hot reload:
```bash
flutter run
```

Para generar código (si usas code generation):
```bash
flutter pub run build_runner build
```

## 📝 Notas

- La aplicación está configurada para conectarse a `http://localhost:3000` por defecto
- Para dispositivos móviles físicos, necesitarás cambiar la URL a la IP de tu máquina local
- Para web, asegúrate de que CORS esté configurado correctamente en el backend
