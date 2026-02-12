import 'package:flutter/material.dart';
import 'package:categoria/widgets/boton_modo.dart';
import 'package:categoria/widgets/boton_color.dart';
import 'package:categoria/core/colores.dart';

void main() {
  runApp(const Categoria());
}

class Categoria extends StatefulWidget {
  const Categoria({super.key});

  @override
  State<Categoria> createState() => _CategoriaState();
}

class _CategoriaState extends State<Categoria> {
  ThemeMode themeMode = ThemeMode.light;
  Color colorSeleccionado = Colores.purpuraOscuro.color;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      themeMode: themeMode,
      theme: ThemeData.light().copyWith(
        colorScheme: ColorScheme.light(primary: colorSeleccionado),
      ),
      darkTheme: ThemeData.dark().copyWith(
        colorScheme: ColorScheme.dark(primary: colorSeleccionado),
      ),
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Categoria'),
          actions: [
            BotonModo(
              cambiarModo: cambiarModo,
            ),
            BotonColor(
              color: Colores.purpuraOscuro,
              onColorSelected: (color) => setState(() => colorSeleccionado = color.color),
            ),
          ],
        ),
        body: Center(child: Text('Hello World!')),
      ),
    );
  }

  void cambiarModo(bool isDarkMode) {
    setState(() => themeMode = isDarkMode ? ThemeMode.dark : ThemeMode.light);
  }
}
