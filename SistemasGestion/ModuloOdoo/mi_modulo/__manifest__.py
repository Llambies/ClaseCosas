# -*- coding: utf-8 -*-
{
    'name': 'Gestor de Tareas',
    'version': '17.0.1.0.0',
    'category': 'Productivity',
    'summary': 'Gestión de tareas con estados, prioridades y fechas límite',
    'description': """
Gestor de Tareas
================

Módulo de gestión de tareas para Odoo 17 desarrollado por Llambies.

**Características principales:**

* **Modelo Tarea** con campos completos:
  - Nombre y descripción
  - Estados: Borrador, En Progreso, Completada, Cancelada
  - Prioridades: Baja, Media, Alta
  - Fecha límite y control de fechas

* **Vistas integradas**: Lista y formulario estándar de Odoo

* **Asistente rápido**: Creación ágil de tareas desde el menú principal

* **Seguridad**: Control de permisos diferenciado para usuarios y administradores
    """,
    'author': 'Llambies',
    'website': 'https://github.com/llambies',
    'license': 'LGPL-3',
    'depends': ['base', 'sale', 'web'],
    'data': [
        'security/ir.model.access.csv',
        'views/tarea_views.xml',
        'wizard/tarea_quick_add_wizard_views.xml',
        'views/menu_views.xml',
    ],
    'installable': True,
    'application': True,
    'auto_install': False,
}
