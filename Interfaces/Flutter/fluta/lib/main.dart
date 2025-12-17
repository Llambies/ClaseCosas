import 'package:fluta/core/tema_custom.dart';
import 'package:fluta/routes/rutas.dart';
import 'package:fluta/screens/pagina_principal.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: temaCustom,
      initialRoute: Rutas.principal,
      onGenerateRoute: Rutas.generarRuta,
      home: const PaginaPrincipal(),
    );
  }
}
