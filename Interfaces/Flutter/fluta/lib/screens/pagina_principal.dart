import 'package:flutter/material.dart';
import 'package:fluta/widgets/logo.dart';
import 'package:fluta/widgets/tarjeta_tarea.dart';
import 'package:fluta/widgets/sin_tareas.dart';
import 'package:fluta/core/colores_app.dart';

class PaginaPrincipal extends StatelessWidget {
  const PaginaPrincipal({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Todo List'),
        backgroundColor: ColoresApp.principal,
      ),
      body: Column(
        children: [
          Logo(titulo: 'Todo List', descripcion: 'Todo List'),
          SinTareas(),
        ],
      ),
    );
  }
}

