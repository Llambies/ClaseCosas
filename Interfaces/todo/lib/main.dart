import 'package:flutter/material.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:flutter_application_2/routes/rutas.dart';
import 'package:flutter_application_2/widgets/escuchador_modo_sistema.dart';
import 'package:provider/provider.dart';

/// Punto de entrada principal de la aplicación de gestión de tareas.
/// 
/// Inicializa el binding de Flutter y ejecuta la aplicación [MainApp].
void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MainApp());
}

/// Widget raíz de la aplicación de gestión de tareas.
/// 
/// Configura el sistema de temas usando [GestorTemas] con Provider,
/// escucha los cambios del modo del sistema operativo y establece
/// las rutas iniciales de la aplicación.
/// 
/// La aplicación soporta tres modos de tema:
/// - Claro: tema con colores claros
/// - Oscuro: tema con colores oscuros
/// - Sistema: sigue el modo del sistema operativo
/// 
/// También soporta diferentes intensidades del esquema de colores Gruvbox:
/// - Soft: colores más suaves
/// - Normal: colores estándar
/// - Hard: colores más intensos
class MainApp extends StatelessWidget {
  /// Crea una instancia de [MainApp].
  const MainApp({super.key});

  /// Construye el árbol de widgets de la aplicación.
  /// 
  /// Configura un [ChangeNotifierProvider] para [GestorTemas] que permite
  /// a toda la aplicación acceder y escuchar cambios en la configuración
  /// del tema. Inicializa el modo del sistema si está configurado para
  /// seguirlo automáticamente.
  /// 
  /// Retorna un [MaterialApp] envuelto en [EscuchadorModoSistema] para
  /// detectar cambios en el modo del sistema operativo.
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
