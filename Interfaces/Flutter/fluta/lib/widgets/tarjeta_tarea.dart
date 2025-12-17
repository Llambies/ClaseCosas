import 'package:flutter/material.dart';
import '../core/colores_app.dart';
import '../models/tarea.dart';

class TarjetaTarea extends StatelessWidget {
  final Tarea tarea;

  const TarjetaTarea({super.key, required this.tarea});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 5,
      margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
      child: ListTile(
        leading: AnimatedSwitcher(
          duration: const Duration(milliseconds: 300), 
          child: Checkbox(value: tarea.estaCompletada, onChanged: (value) {}, activeColor: ColoresApp.secundario,),
        ),
        title: Text(tarea.titulo, style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
        subtitle: Text(tarea.descripcion, style: const TextStyle(fontSize: 14)),
      ),
    );
  }
}