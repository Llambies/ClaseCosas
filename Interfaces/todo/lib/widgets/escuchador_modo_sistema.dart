import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:provider/provider.dart';

class EscuchadorModoSistema extends StatefulWidget {
  final Widget child;

  const EscuchadorModoSistema({super.key, required this.child});

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
