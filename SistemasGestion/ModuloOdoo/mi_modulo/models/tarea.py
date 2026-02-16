# -*- coding: utf-8 -*-
from odoo import models, fields, api


class Tarea(models.Model):
    _name = 'mi_modulo.tarea'
    _description = 'Tarea'

    name = fields.Char(string='Nombre', required=True)
    description = fields.Text(string='Descripción')
    estado = fields.Selection(
        selection=[
            ('borrador', 'Borrador'),
            ('en_progreso', 'En Progreso'),
            ('completada', 'Completada'),
            ('cancelada', 'Cancelada'),
        ],
        string='Estado',
        default='borrador',
        required=True
    )
    prioridad = fields.Selection(
        selection=[
            ('baja', 'Baja'),
            ('media', 'Media'),
            ('alta', 'Alta'),
        ],
        string='Prioridad',
        default='media'
    )
    fecha_limite = fields.Date(string='Fecha Límite')
    fecha_hora_limite = fields.Datetime(
        string='Fecha y Hora Límite',
        help='Fecha y hora límite (para tareas con hora específica)'
    )
    sale_order_id = fields.Many2one(
        'sale.order',
        string='Pedido de Venta',
        readonly=True,
        ondelete='set null',
        help='Pedido que generó esta tarea'
    )
    fecha_creacion = fields.Datetime(
        string='Fecha de Creación',
        default=lambda self: fields.Datetime.now(),
        readonly=True
    )
    activo = fields.Boolean(string='Activo', default=True)
