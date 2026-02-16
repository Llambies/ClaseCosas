# Gestor de Tareas para Odoo 17

Módulo de gestión de tareas para Odoo 17 desarrollado por **Llambies**. Incluye un modelo de **Tareas** con gestión de estados, prioridades y fechas límite.

## Estructura del módulo

```
mi_modulo/
├── __init__.py
├── __manifest__.py          # Metadata del módulo
├── models/
│   ├── __init__.py
│   └── tarea.py            # Modelo de datos
├── views/
│   ├── tarea_views.xml     # Vistas lista y formulario
│   └── menu_views.xml      # Menús y acciones
└── security/
    └── ir.model.access.csv # Permisos de acceso
```

## Instalación

### Con Docker (odoo2)

1. **Copia el módulo** al directorio `addons` de tu proyecto Odoo:

   ```bash
   # Copia el módulo a odoo2
   cp -r /home/owoma/ClaseCosas/SistemasGestion/ModuloOdoo/mi_modulo \
         /home/owoma/ClaseCosas/SistemasGestion/odoo2/addons/
   ```

   O crea un enlace simbólico si prefieres trabajar sobre el código original:

   ```bash
   mkdir -p /home/owoma/ClaseCosas/SistemasGestion/odoo2/addons
   ln -s /home/owoma/ClaseCosas/SistemasGestion/ModuloOdoo/mi_modulo \
         /home/owoma/ClaseCosas/SistemasGestion/odoo2/addons/
   ```

2. **Reinicia Odoo** (si está corriendo):

   ```bash
   cd /home/owoma/ClaseCosas/SistemasGestion/odoo2
   docker compose restart odoo
   ```

3. **Activa el módulo** en Odoo:
   - Ve a **Aplicaciones**
   - Busca "Gestor de Tareas"
   - Haz clic en **Instalar**

### Sin Docker

Coloca la carpeta `mi_modulo` dentro del directorio de addons de tu instalación Odoo (por ejemplo `addons/` o `custom_addons/`) y añade esa ruta a la configuración de `addons_path` en tu `odoo.conf`.

## Funcionalidad

- **Modelo Tarea** con campos:
  - Nombre (obligatorio)
  - Descripción
  - Estado: Borrador, En Progreso, Completada, Cancelada
  - Prioridad: Baja, Media, Alta
  - Fecha límite
  - Fecha de creación

- **Vistas**: lista y formulario estándar
- **Menú**: "Gestor de Tareas" > "Tareas" en el menú principal

## Personalización

Puedes modificar el módulo según tus necesidades:

- Añadir más modelos en `models/`
- Crear relaciones entre modelos (`Many2one`, `One2many`)
- Añadir más vistas o reportes
- Definir reglas de seguridad más granulares en `security/`
- Añadir datos por defecto en `data/`
