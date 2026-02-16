import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:provider/provider.dart';

/// Widget que escucha los cambios en el modo del sistema operativo.
/// 
/// Implementa [WidgetsBindingObserver] para detectar cuando el sistema
/// operativo cambia entre modo claro y oscuro. Cuando el modo de tema
/// de la aplicación está configurado como [ModoTema.sistema], actualiza
/// automáticamente el tema de la aplicación para seguir el modo del sistema.
/// 
/// Este widget debe envolver el [MaterialApp] para que funcione correctamente.
class EscuchadorModoSistema extends StatefulWidget {
  /// Crea una instancia de [EscuchadorModoSistema].
  /// 
  /// [child] el widget hijo que se mostrará (normalmente el MaterialApp).
  const EscuchadorModoSistema({super.key, required this.child});

  /// El widget hijo que se mostrará.
  final Widget child;

  @override
  State<EscuchadorModoSistema> createState() => _EscuchadorModoSistemaState();
}

class _EscuchadorModoSistemaState extends State<EscuchadorModoSistema>
    with WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  /// Se llama cuando el sistema operativo cambia su modo claro/oscuro.
  /// 
  /// Si el modo de tema actual es [ModoTema.sistema], actualiza el tema
  /// de la aplicación para que coincida con el modo del sistema operativo.
  @override
  void didChangePlatformBrightness() {
    super.didChangePlatformBrightness();
    final gestorTemas = Provider.of<GestorTemas>(context, listen: false);
    if (gestorTemas.modoTema == ModoTema.sistema) {
      final brightness = WidgetsBinding.instance.platformDispatcher.platformBrightness;
      gestorTemas.actualizarModoOscuroSistema(brightness == Brightness.dark);
    }
  }

  @override
  Widget build(BuildContext context) {
    return widget.child;
  }
}
