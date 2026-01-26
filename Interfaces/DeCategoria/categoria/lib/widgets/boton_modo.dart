import 'package:flutter/material.dart';
import 'package:categoria/core/colores.dart';

class BotonModo extends StatelessWidget {
  final Function(bool) cambiarModo;
  const BotonModo({
    super.key,
    required this.cambiarModo,
  });

  @override
  Widget build(BuildContext context) {
    final estaEnModoOscuro = Theme.of(context).brightness == Brightness.dark;
    return IconButton(
      onPressed: () => cambiarModo(!estaEnModoOscuro),
      icon: Icon(estaEnModoOscuro ? Icons.light_mode : Icons.dark_mode),
      color: Colores.purpuraOscuro.color,
    );
  }
}
