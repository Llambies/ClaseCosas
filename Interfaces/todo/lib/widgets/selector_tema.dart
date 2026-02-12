import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:flutter_application_2/core/colores_app.dart';
import 'package:provider/provider.dart';

/// Selector de tema con opciones de modo (claro/oscuro/sistema) e intensidad Gruvbox.
class SelectorTema extends StatelessWidget {
  const SelectorTema({super.key});

  // Mapas de configuración para reducir código repetitivo
  static const _modosTema = {
    'claro': (ModoTema.claro, Icons.light_mode, 'Claro'),
    'oscuro': (ModoTema.oscuro, Icons.dark_mode, 'Oscuro'),
    'sistema': (ModoTema.sistema, Icons.brightness_auto, 'Sistema'),
  };

  static const _intensidades = {
    'soft': (IntensidadGruvbox.soft, 'Soft'),
    'normal': (IntensidadGruvbox.normal, 'Normal'),
    'hard': (IntensidadGruvbox.hard, 'Hard'),
  };

  @override
  Widget build(BuildContext context) {
    final gestorTemas = Provider.of<GestorTemas>(context);
    final primaryColor = Theme.of(context).colorScheme.primary;

    return PopupMenuButton<String>(
      icon: const Icon(Icons.palette),
      tooltip: 'Configuración de tema',
      onSelected: (valor) => _manejarSeleccion(valor, gestorTemas),
      itemBuilder: (context) => [
        _construirEncabezado('Modo'),
        ..._modosTema.entries.map((entry) => _construirItemModo(
              entry.key,
              entry.value,
              gestorTemas.modoTema,
              primaryColor,
            )),
        const PopupMenuDivider(),
        _construirEncabezado('Intensidad'),
        ..._intensidades.entries.map((entry) => _construirItemIntensidad(
              entry.key,
              entry.value,
              gestorTemas.intensidad,
              gestorTemas.esModoOscuro,
              primaryColor,
            )),
      ],
    );
  }

  void _manejarSeleccion(String valor, GestorTemas gestorTemas) {
    if (valor.startsWith('modo_')) {
      final nombreModo = valor.substring(5);
      final config = _modosTema[nombreModo];
      if (config != null) {
        gestorTemas.cambiarModoTema(config.$1);
      }
    } else if (valor.startsWith('intensidad_')) {
      final nombreIntensidad = valor.substring(11);
      final config = _intensidades[nombreIntensidad];
      if (config != null) {
        gestorTemas.cambiarIntensidad(config.$1);
      }
    }
  }

  PopupMenuItem<String> _construirEncabezado(String titulo) {
    return PopupMenuItem(
      enabled: false,
      child: Text(
        titulo,
        style: const TextStyle(fontWeight: FontWeight.bold),
      ),
    );
  }

  PopupMenuItem<String> _construirItemModo(
    String key,
    (ModoTema, IconData, String) config,
    ModoTema modoActual,
    Color primaryColor,
  ) {
    final (modo, icono, nombre) = config;
    final estaSeleccionado = modoActual == modo;

    return PopupMenuItem(
      value: 'modo_$key',
      child: Row(
        children: [
          Icon(icono, color: estaSeleccionado ? primaryColor : null),
          const SizedBox(width: 8),
          Text(nombre),
          if (estaSeleccionado) ...[
            const Spacer(),
            Icon(Icons.check, color: primaryColor),
          ],
        ],
      ),
    );
  }

  PopupMenuItem<String> _construirItemIntensidad(
    String key,
    (IntensidadGruvbox, String) config,
    IntensidadGruvbox intensidadActual,
    bool esModoOscuro,
    Color primaryColor,
  ) {
    final (intensidad, nombre) = config;
    final estaSeleccionado = intensidadActual == intensidad;

    // Colores de muestra para cada intensidad
    Color colorMuestra;
    switch (intensidad) {
      case IntensidadGruvbox.soft:
        colorMuestra = esModoOscuro ? ColoresApp.darkSoft : ColoresApp.lightSoft;
        break;
      case IntensidadGruvbox.normal:
        colorMuestra = esModoOscuro ? ColoresApp.darkNormal : ColoresApp.lightNormal;
        break;
      case IntensidadGruvbox.hard:
        colorMuestra = esModoOscuro ? ColoresApp.darkHard : ColoresApp.lightHard;
        break;
    }

    return PopupMenuItem(
      value: 'intensidad_$key',
      child: Row(
        children: [
          Container(
            width: 20,
            height: 20,
            decoration: BoxDecoration(
              color: colorMuestra,
              shape: BoxShape.circle,
              border: Border.all(
                color: estaSeleccionado ? primaryColor : ColoresApp.gray,
                width: estaSeleccionado ? 2 : 1,
              ),
            ),
          ),
          const SizedBox(width: 8),
          Text(nombre),
          if (estaSeleccionado) ...[
            const Spacer(),
            Icon(Icons.check, color: primaryColor),
          ],
        ],
      ),
    );
  }
}
