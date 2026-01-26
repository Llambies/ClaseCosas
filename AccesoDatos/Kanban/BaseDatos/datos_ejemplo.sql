-- Script SQL para insertar datos de ejemplo en la base de datos Kanban
-- Base de datos: kanban_db
-- Usuario: kanban_user

-- Limpiar datos existentes (opcional, descomentar si quieres empezar desde cero)
-- TRUNCATE TABLE tarjeta_etiqueta CASCADE;
-- TRUNCATE TABLE tarjetas CASCADE;
-- TRUNCATE TABLE columnas CASCADE;
-- TRUNCATE TABLE tableros CASCADE;
-- TRUNCATE TABLE etiquetas CASCADE;
-- TRUNCATE TABLE usuarios CASCADE;

-- ============================================
-- 1. INSERTAR USUARIOS
-- ============================================
INSERT INTO usuarios (nombre, email, contraseña) VALUES
('Juan Pérez', 'juan.perez@email.com', 'password123'),
('María García', 'maria.garcia@email.com', 'password456'),
('Carlos López', 'carlos.lopez@email.com', 'password789'),
('Ana Martínez', 'ana.martinez@email.com', 'password012');

-- ============================================
-- 2. INSERTAR ETIQUETAS
-- ============================================
INSERT INTO etiquetas (nombre, color) VALUES
('Urgente', '#FF0000'),
('Importante', '#FFA500'),
('En Progreso', '#0000FF'),
('Completado', '#00FF00'),
('Bug', '#FF1493'),
('Mejora', '#9370DB'),
('Documentación', '#20B2AA'),
('Revisión', '#FFD700');

-- ============================================
-- 3. INSERTAR TABLEROS
-- ============================================
INSERT INTO tableros (nombre, usuario_id) VALUES
('Proyecto Web', 1),
('Aplicación Móvil', 1),
('Sistema de Gestión', 2),
('Proyecto Personal', 3),
('Tareas del Equipo', 2);

-- ============================================
-- 4. INSERTAR COLUMNAS
-- ============================================
-- Columnas para "Proyecto Web" (tablero_id = 1)
INSERT INTO columnas (nombre, tablero_id) VALUES
('Por Hacer', 1),
('En Progreso', 1),
('En Revisión', 1),
('Completado', 1);

-- Columnas para "Aplicación Móvil" (tablero_id = 2)
INSERT INTO columnas (nombre, tablero_id) VALUES
('Backlog', 2),
('Desarrollo', 2),
('Testing', 2),
('Producción', 2);

-- Columnas para "Sistema de Gestión" (tablero_id = 3)
INSERT INTO columnas (nombre, tablero_id) VALUES
('Pendiente', 3),
('En Trabajo', 3),
('Finalizado', 3);

-- Columnas para "Proyecto Personal" (tablero_id = 4)
INSERT INTO columnas (nombre, tablero_id) VALUES
('Ideas', 4),
('En Desarrollo', 4),
('Terminado', 4);

-- Columnas para "Tareas del Equipo" (tablero_id = 5)
INSERT INTO columnas (nombre, tablero_id) VALUES
('Nuevas Tareas', 5),
('Asignadas', 5),
('Completadas', 5);

-- ============================================
-- 5. INSERTAR TARJETAS
-- ============================================
-- Tarjetas para "Proyecto Web" - Columna "Por Hacer" (columna_id = 1)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Diseñar interfaz de usuario', 'Crear mockups y wireframes para la nueva interfaz', 1),
('Configurar base de datos', 'Configurar esquema inicial y migraciones', 1);

-- Tarjetas para "Proyecto Web" - Columna "En Progreso" (columna_id = 2)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Implementar autenticación', 'Desarrollar sistema de login y registro de usuarios', 2),
('Crear API REST', 'Implementar endpoints para gestión de datos', 2);

-- Tarjetas para "Proyecto Web" - Columna "En Revisión" (columna_id = 3)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Optimizar consultas SQL', 'Revisar y optimizar las consultas de base de datos', 3);

-- Tarjetas para "Proyecto Web" - Columna "Completado" (columna_id = 4)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Configurar servidor', 'Configurar servidor de producción y despliegue', 4),
('Documentación inicial', 'Escribir documentación básica del proyecto', 4);

-- Tarjetas para "Aplicación Móvil" - Columna "Backlog" (columna_id = 5)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Diseño de iconos', 'Crear iconos personalizados para la aplicación', 5),
('Integración con API', 'Conectar la app móvil con el backend', 5);

-- Tarjetas para "Aplicación Móvil" - Columna "Desarrollo" (columna_id = 6)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Pantalla de inicio', 'Desarrollar la pantalla principal de la aplicación', 6),
('Sistema de notificaciones', 'Implementar push notifications', 6);

-- Tarjetas para "Aplicación Móvil" - Columna "Testing" (columna_id = 7)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Pruebas unitarias', 'Escribir y ejecutar tests unitarios', 7);

-- Tarjetas para "Sistema de Gestión" - Columna "Pendiente" (columna_id = 9)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Análisis de requisitos', 'Documentar todos los requisitos del sistema', 9),
('Planificación de sprints', 'Organizar tareas en sprints de desarrollo', 9);

-- Tarjetas para "Sistema de Gestión" - Columna "En Trabajo" (columna_id = 10)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Módulo de usuarios', 'Desarrollar gestión de usuarios y permisos', 10);

-- Tarjetas para "Proyecto Personal" - Columna "Ideas" (columna_id = 13)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Aplicación de tareas', 'Crear una app para gestionar tareas personales', 13),
('Blog personal', 'Desarrollar un blog con sistema de comentarios', 13);

-- Tarjetas para "Tareas del Equipo" - Columna "Nuevas Tareas" (columna_id = 15)
INSERT INTO tarjetas (titulo, descripcion, columna_id) VALUES
('Reunión de planificación', 'Organizar reunión para planificar el próximo sprint', 15),
('Actualizar documentación', 'Revisar y actualizar la documentación del proyecto', 15);

-- ============================================
-- 6. INSERTAR RELACIONES TARJETA-ETIQUETA (Many-to-Many)
-- ============================================
-- Asignar etiquetas a las tarjetas
INSERT INTO tarjeta_etiqueta (tarjeta_id, etiqueta_id) VALUES
-- Tarjeta 1: Diseñar interfaz de usuario
(1, 2),  -- Importante
(1, 7),  -- Documentación
-- Tarjeta 2: Configurar base de datos
(2, 1),  -- Urgente
(2, 2),  -- Importante
-- Tarjeta 3: Implementar autenticación
(3, 1),  -- Urgente
(3, 3),  -- En Progreso
-- Tarjeta 4: Crear API REST
(4, 2),  -- Importante
(4, 3),  -- En Progreso
-- Tarjeta 5: Optimizar consultas SQL
(5, 2),  -- Importante
(5, 4),  -- Completado
(5, 6),  -- Mejora
-- Tarjeta 6: Configurar servidor
(6, 4),  -- Completado
-- Tarjeta 7: Documentación inicial
(7, 4),  -- Completado
(7, 7),  -- Documentación
-- Tarjeta 8: Diseño de iconos
(8, 2),  -- Importante
-- Tarjeta 9: Integración con API
(9, 1),  -- Urgente
(9, 3),  -- En Progreso
-- Tarjeta 10: Pantalla de inicio
(10, 3), -- En Progreso
-- Tarjeta 11: Sistema de notificaciones
(11, 3), -- En Progreso
(11, 6), -- Mejora
-- Tarjeta 12: Pruebas unitarias
(12, 4), -- Completado
(12, 8), -- Revisión
-- Tarjeta 13: Análisis de requisitos
(13, 2), -- Importante
(13, 7), -- Documentación
-- Tarjeta 14: Planificación de sprints
(14, 2), -- Importante
-- Tarjeta 15: Módulo de usuarios
(15, 1), -- Urgente
(15, 3), -- En Progreso
-- Tarjeta 16: Aplicación de tareas
(16, 6), -- Mejora
-- Tarjeta 17: Blog personal
(17, 6), -- Mejora
-- Tarjeta 18: Reunión de planificación
(18, 1), -- Urgente
(18, 2), -- Importante
-- Tarjeta 19: Actualizar documentación
(19, 2), -- Importante
(19, 7); -- Documentación

-- ============================================
-- CONSULTAS DE VERIFICACIÓN (opcional)
-- ============================================
-- Verificar datos insertados:
-- SELECT COUNT(*) as total_usuarios FROM usuarios;
-- SELECT COUNT(*) as total_tableros FROM tableros;
-- SELECT COUNT(*) as total_columnas FROM columnas;
-- SELECT COUNT(*) as total_tarjetas FROM tarjetas;
-- SELECT COUNT(*) as total_etiquetas FROM etiquetas;
-- SELECT COUNT(*) as total_relaciones FROM tarjeta_etiqueta;

-- Ver tableros con sus usuarios:
-- SELECT t.id, t.nombre, u.nombre as usuario FROM tableros t JOIN usuarios u ON t.usuario_id = u.id;

-- Ver tarjetas con sus columnas y tableros:
-- SELECT tar.id, tar.titulo, col.nombre as columna, tab.nombre as tablero 
-- FROM tarjetas tar 
-- JOIN columnas col ON tar.columna_id = col.id 
-- JOIN tableros tab ON col.tablero_id = tab.id;

-- Ver tarjetas con sus etiquetas:
-- SELECT tar.titulo, et.nombre as etiqueta, et.color 
-- FROM tarjetas tar 
-- JOIN tarjeta_etiqueta te ON tar.id = te.tarjeta_id 
-- JOIN etiquetas et ON te.etiqueta_id = et.id 
-- ORDER BY tar.id;
