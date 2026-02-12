import 'package:flutter/material.dart';
import 'package:flutter_application_2/screens/pagina_agregar_tarea.dart';
import 'package:flutter_application_2/screens/pagina_principal.dart';

class Rutas {
  static const String paginaPrincipal = '/';
  static const String pagAgregarTarea = '/agregar_tarea';


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
