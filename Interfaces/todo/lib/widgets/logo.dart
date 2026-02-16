import 'package:flutter/material.dart';

/// Widget que muestra el logo de la aplicación.
/// 
/// Carga y muestra la imagen del logo desde los assets de la aplicación.
/// El logo se ajusta automáticamente al tamaño disponible manteniendo
/// su relación de aspecto.
class Logo extends StatelessWidget {
  /// Crea una instancia de [Logo].
  const Logo({super.key});

  @override
  Widget build(BuildContext context) {
    return Image.asset(
      'assets/images/logo.png',
      height: 180,
      width: 180,
      fit: BoxFit.contain,
    );
  }
}
