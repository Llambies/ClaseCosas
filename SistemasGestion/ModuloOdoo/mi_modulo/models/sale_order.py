# -*- coding: utf-8 -*-
from odoo import models


class SaleOrder(models.Model):
    _inherit = 'sale.order'

    def action_confirm(self):
        """Crear una tarea asociada al pedido al confirmarlo."""
        result = super().action_confirm()
        for order in self:
            # Evitar crear tarea duplicada si ya existe una para este pedido
            if self.env['mi_modulo.tarea'].search_count([
                ('sale_order_id', '=', order.id)
            ]):
                continue
            self.env['mi_modulo.tarea'].create({
                'name': f"Pedido {order.name}",
                'description': self._generar_descripcion_tarea(order),
                'estado': 'borrador',
                'prioridad': 'media',
                'sale_order_id': order.id,
            })
        return result

    def _generar_descripcion_tarea(self, order):
        """Genera la descripción de la tarea a partir del pedido."""
        lineas = [f"Cliente: {order.partner_id.name}"]
        if order.date_order:
            lineas.append(
                f"Fecha: {order.date_order.strftime('%d/%m/%Y %H:%M')}"
            )
        if order.amount_total:
            lineas.append(f"Total: {order.currency_id.symbol} {order.amount_total:,.2f}")
        return '\n'.join(lineas)
