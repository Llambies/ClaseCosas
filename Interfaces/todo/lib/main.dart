import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:flutter_application_2/routes/rutas.dart';
import 'package:flutter_application_2/widgets/escuchador_modo_sistema.dart';
import 'package:provider/provider.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) {
        final gestor = GestorTemas();
        // Inicializar con el modo del sistema
        final brightness =
            WidgetsBinding.instance.platformDispatcher.platformBrightness;
        if (gestor.modoTema == ModoTema.sistema) {
          gestor.actualizarModoOscuroSistema(brightness == Brightness.dark);
        }
        return gestor;
      },
      child: Consumer<GestorTemas>(
        builder: (context, gestorTemas, _) {
          return EscuchadorModoSistema(
            child: MaterialApp(
              debugShowCheckedModeBanner: false,
              // El tema se genera dinámicamente desde GestorTemas
              theme: gestorTemas.obtenerTema(),
              initialRoute: Rutas.paginaPrincipal,
              onGenerateRoute: Rutas.generarRuta,
            ),
          );
        },
      ),
    );
  }
}
