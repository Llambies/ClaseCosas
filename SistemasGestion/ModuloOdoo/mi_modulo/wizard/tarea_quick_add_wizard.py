# -*- coding: utf-8 -*-
"""
Wizard para agregar tareas rápidamente con análisis de lenguaje natural.
Ejemplo: "reunión para mañana por la mañana" → nombre="reunión", fecha=mañana, hora=9:00
"""
import re
from datetime import datetime, timedelta

import pytz
from odoo import models, fields
from odoo.exceptions import UserError


class TareaQuickAddWizard(models.TransientModel):
    _name = 'mi_modulo.tarea.quick_add.wizard'
    _description = 'Añadir Tarea Rápida'

    texto = fields.Char(
        string='Tarea',
        required=True,
        placeholder='Ej: reunión para mañana por la mañana'
    )

    def _parsear_texto(self, texto):
        """
        Parsea texto en lenguaje natural y extrae nombre, fecha y hora.
        Retorna: (nombre, fecha_hora_datetime o None)
        """
        if not texto or not texto.strip():
            return texto.strip(), None

        texto = texto.strip()
        texto_lower = texto.lower()
        ahora = datetime.now()
        fecha_resultado = None
        hora_resultado = None

        # --- Extraer HORA ---
        hora_patrones = [
            # "a las 9", "a las 9:30", "a las 15"
            (r'\ba las (\d{1,2})(?::(\d{2}))?\s*(?:h|hrs?)?\b', self._parse_hora_numero),
            # "a las 9am", "a las 3pm"
            (r'\ba las (\d{1,2})\s*(am|pm)\b', self._parse_hora_am_pm),
            # "por la mañana" -> 9:00
            (r'\bpor la mañana\b', (9, 0)),
            (r'\ben la mañana\b', (9, 0)),
            (r'\ba la mañana\b', (9, 0)),
            (r'\bde la mañana\b', (9, 0)),
            # "por la tarde" -> 15:00
            (r'\bpor la tarde\b', (15, 0)),
            (r'\ben la tarde\b', (15, 0)),
            (r'\ba la tarde\b', (15, 0)),
            (r'\bde la tarde\b', (15, 0)),
            # "por la noche" -> 21:00
            (r'\bpor la noche\b', (21, 0)),
            (r'\ben la noche\b', (21, 0)),
            (r'\ba la noche\b', (21, 0)),
            (r'\bde la noche\b', (21, 0)),
        ]

        for patron, valor in hora_patrones:
            match = re.search(patron, texto_lower, re.IGNORECASE)
            if match:
                if callable(valor):
                    hora_resultado = valor(match)
                else:
                    hora_resultado = valor
                texto = re.sub(patron, ' ', texto, flags=re.IGNORECASE)
                break

        # --- Extraer FECHA ---
        dias_semana = {
            'lunes': 0, 'martes': 1, 'miércoles': 2, 'miercoles': 2,
            'jueves': 3, 'viernes': 4, 'sábado': 5, 'sabado': 5,
            'domingo': 6
        }

        fecha_patrones = [
            # "hoy"
            (r'\bpara hoy\b', lambda: ahora.date()),
            (r'\bhoy\b', lambda: ahora.date()),
            # "mañana"
            (r'\bpara mañana\b', lambda: (ahora + timedelta(days=1)).date()),
            (r'\bmañana\b', lambda: (ahora + timedelta(days=1)).date()),
            # "pasado mañana"
            (r'\bpara pasado mañana\b', lambda: (ahora + timedelta(days=2)).date()),
            (r'\bpasado mañana\b', lambda: (ahora + timedelta(days=2)).date()),
        ]

        for patron, func in fecha_patrones:
            match = re.search(patron, texto_lower)
            if match:
                fecha_resultado = func()
                texto = re.sub(patron, ' ', texto, flags=re.IGNORECASE)
                break

        # "el lunes", "el próximo martes", etc.
        if not fecha_resultado:
            for dia_nombre, dia_num in dias_semana.items():
                for prefijo in [r'\bel próximo ', r'\bpróximo ', r'\b el ', r'\bpara el ']:
                    patron = prefijo + dia_nombre + r'\b'
                    if re.search(patron, texto_lower):
                        fecha_resultado = self._proximo_dia_semana(
                            ahora, dia_num
                        )
                        texto = re.sub(patron, ' ', texto, flags=re.IGNORECASE)
                        break
                if fecha_resultado:
                    break

        # "el 15 de marzo", "15/03"
        if not fecha_resultado:
            fecha_match = re.search(
                r'\b(\d{1,2})\s+de\s+(enero|febrero|marzo|abril|mayo|junio|'
                r'julio|agosto|septiembre|octubre|noviembre|diciembre)\b',
                texto_lower
            )
            if fecha_match:
                dia, mes_str = int(fecha_match.group(1)), fecha_match.group(2)
                meses = {
                    'enero': 1, 'febrero': 2, 'marzo': 3, 'abril': 4,
                    'mayo': 5, 'junio': 6, 'julio': 7, 'agosto': 8,
                    'septiembre': 9, 'octubre': 10, 'noviembre': 11,
                    'diciembre': 12
                }
                mes = meses.get(mes_str, ahora.month)
                año = ahora.year
                try:
                    fecha_resultado = datetime(año, mes, dia).date()
                    texto = re.sub(
                        r'\d{1,2}\s+de\s+' + mes_str + r'\b',
                        ' ', texto, flags=re.IGNORECASE
                    )
                except ValueError:
                    pass

        # Limpiar el nombre: quitar "para", espacios extra, etc.
        nombre = re.sub(r'\bpara\b', ' ', texto, flags=re.IGNORECASE)
        nombre = re.sub(r'\s+', ' ', nombre).strip()
        if not nombre:
            nombre = 'Nueva tarea'

        # Construir fecha_hora final
        fecha_hora_final = None
        if fecha_resultado:
            hora_default = (9, 0) if not hora_resultado else hora_resultado
            fecha_hora_final = datetime.combine(
                fecha_resultado,
                datetime.min.time().replace(
                    hour=hora_default[0],
                    minute=hora_default[1]
                )
            )
        elif hora_resultado:
            # Solo hora, usar hoy
            fecha_hora_final = datetime.combine(
                ahora.date(),
                datetime.min.time().replace(
                    hour=hora_resultado[0],
                    minute=hora_resultado[1]
                )
            )

        return nombre, fecha_hora_final

    def _parse_hora_numero(self, match):
        """Convierte match 'a las 9' o 'a las 9:30' a (hora, minuto)."""
        hora = int(match.group(1))
        minuto = int(match.group(2)) if match.group(2) else 0
        if hora < 24:  # Formato 24h
            return (hora, minuto)
        return (9, 0)  # fallback

    def _parse_hora_am_pm(self, match):
        """Convierte '9am' o '3pm' a (hora, minuto)."""
        hora = int(match.group(1))
        meridiano = match.group(2).lower()
        if meridiano == 'pm' and hora < 12:
            hora += 12
        elif meridiano == 'am' and hora == 12:
            hora = 0
        return (hora, 0)

    def _proximo_dia_semana(self, desde, dia_semana_obj):
        """Obtiene la próxima ocurrencia del día de la semana (0=lunes)."""
        delta = (dia_semana_obj - desde.weekday()) % 7
        if delta == 0:
            delta = 7  # Si es hoy, dar el próximo
        return (desde + timedelta(days=delta)).date()

    def _local_a_utc(self, dt_naive_local):
        """
        Convierte un datetime naive (hora local del usuario) a UTC.
        Odoo almacena en UTC; si no convertimos, al mostrar suma la zona horaria.
        """
        tz_name = self.env.user.tz or 'UTC'
        user_tz = pytz.timezone(tz_name)
        local_dt = user_tz.localize(dt_naive_local, is_dst=None)
        utc_dt = local_dt.astimezone(pytz.UTC)
        return utc_dt.replace(tzinfo=None)

    def action_crear_tarea(self):
        """Crea la tarea con los datos parseados."""
        self.ensure_one()
        nombre, fecha_hora = self._parsear_texto(self.texto)
        if not nombre:
            raise UserError('No se pudo extraer un nombre para la tarea.')

        vals = {
            'name': nombre,
            'estado': 'borrador',
            'prioridad': 'media',
        }
        if fecha_hora:
            vals['fecha_limite'] = fecha_hora.date()
            vals['fecha_hora_limite'] = self._local_a_utc(fecha_hora)

        self.env['mi_modulo.tarea'].create(vals)
        # Cerrar popup y permanecer en la lista de tareas
        return {
            'type': 'ir.actions.act_window',
            'res_model': 'mi_modulo.tarea',
            'view_mode': 'tree,form',
            'target': 'current',
        }
