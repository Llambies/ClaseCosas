import 'package:flutter/material.dart';
import 'package:flutter_application_2/screens/pagina_agregar_tarea.dart';
import 'package:flutter_application_2/screens/pagina_principal.dart';

/// Clase estática que gestiona las rutas de la aplicación.
/// 
/// Define las rutas disponibles y genera los widgets correspondientes
/// cuando se navega a cada ruta. Utiliza el sistema de rutas nombradas
/// de Flutter para la navegación entre pantallas.
class Rutas {
  /// Ruta de la pantalla principal de la aplicación.
  static const String paginaPrincipal = '/';
  
  /// Ruta de la pantalla para agregar o editar una tarea.
  static const String pagAgregarTarea = '/agregar_tarea';

  /// Genera una ruta basándose en el nombre de la ruta solicitada.
  /// 
  /// Retorna un [Route] con el widget correspondiente a la ruta solicitada.
  /// Si la ruta no existe, retorna una ruta de error.
  /// 
  /// [ajustes] configuración de la ruta que incluye el nombre de la ruta.
  /// 
  /// Retorna un [Route] con el widget apropiado o una pantalla de error.
  static Route<dynamic> generarRuta(RouteSettings ajustes) {
    switch (ajustes.name) {
      case paginaPrincipal:
        return MaterialPageRoute(builder: (context) => const PaginaPrincipal());
      case pagAgregarTarea:
        return MaterialPageRoute(builder: (context) => const PaginaAgregarTarea());
      default:
        return MaterialPageRoute(
            builder: (context) => const Scaffold(
              body: Center(child: Text("Error: ruta no encontrada"))
            ));
    }
  }
}
