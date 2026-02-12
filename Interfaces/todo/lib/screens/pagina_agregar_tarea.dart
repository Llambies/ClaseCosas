import 'package:flutter/material.dart';
import 'package:flutter_application_2/models/tarea.dart';
import 'package:flutter_application_2/widgets/logo.dart';

class PaginaAgregarTarea extends StatefulWidget {
  const PaginaAgregarTarea({super.key, this.tareaParaEditar});

  final Tarea? tareaParaEditar;

  @override
  State<PaginaAgregarTarea> createState() => _PaginaAgregarTareaState();
}

class _PaginaAgregarTareaState extends State<PaginaAgregarTarea> {
  final TextEditingController _titulo = TextEditingController();
  final TextEditingController _descripcion = TextEditingController();
  Categoria _categoriaSeleccionada = Categoria.otro;

  @override
  void initState() {
    super.initState();
    // Si nos pasaron una tarea para editar, rellenamos los campos
    if (widget.tareaParaEditar != null) {
      _titulo.text = widget.tareaParaEditar!.titulo;
      _descripcion.text = widget.tareaParaEditar!.descripcion;
      _categoriaSeleccionada = widget.tareaParaEditar!.categoria;
    }
  }

  @override
  void dispose() {
    _titulo.dispose();
    _descripcion.dispose();
    super.dispose();
  }

  void _guardarTarea() {
    if (_titulo.text.isNotEmpty) {
      // Si estamos editando, usamos copyWith para mantener el ID
      final tarea = widget.tareaParaEditar != null
          ? widget.tareaParaEditar!.copyWith(
              titulo: _titulo.text,
              descripcion: _descripcion.text,
              categoria: _categoriaSeleccionada,
            )
          : Tarea(
              titulo: _titulo.text,
              descripcion: _descripcion.text,
              estaTerminada: false,
              categoria: _categoriaSeleccionada,
            );
      Navigator.pop(context, tarea);
    } else {
      // Mostrar mensaje de error si el título está vacío
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('El título es obligatorio'),
          duration: Duration(seconds: 2),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Logo(),
        centerTitle: true,
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            TextField(
              controller: _titulo,
              decoration: InputDecoration(
                labelText: 'Título',
                prefixIcon: Icon(Icons.task_alt, color: Theme.of(context).colorScheme.secondary),
                border: const OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(12)),
                ),
                filled: true,
                fillColor: Theme.of(context).scaffoldBackgroundColor,
                labelStyle: TextStyle(color: Theme.of(context).colorScheme.onSurface),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _descripcion,
              decoration: InputDecoration(
                labelText: 'Descripción',
                prefixIcon: Icon(Icons.task_alt, color: Theme.of(context).colorScheme.secondary),
                border: const OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(12)),
                ),
                filled: true,
                fillColor: Theme.of(context).scaffoldBackgroundColor,
                labelStyle: TextStyle(color: Theme.of(context).colorScheme.onSurface),
              ),
              maxLines: 4,
            ),
            const SizedBox(height: 16),
            DropdownButtonFormField<Categoria>(
              value: _categoriaSeleccionada,
              decoration: InputDecoration(
                labelText: 'Categoría',
                prefixIcon: Icon(
                  Icons.category_outlined,
                  color: Theme.of(context).colorScheme.secondary,
                ),
                border: const OutlineInputBorder(
                  borderRadius: BorderRadius.all(Radius.circular(12)),
                ),
                filled: true,
                fillColor: Theme.of(context).scaffoldBackgroundColor,
                labelStyle: TextStyle(color: Theme.of(context).colorScheme.onSurface),
              ),
              items: const [
                DropdownMenuItem(
                  value: Categoria.personal,
                  child: Text('Personal'),
                ),
                DropdownMenuItem(
                  value: Categoria.trabajo,
                  child: Text('Trabajo'),
                ),
                DropdownMenuItem(value: Categoria.otro, child: Text('Otro')),
              ],
              onChanged: (value) => setState(() {
                _categoriaSeleccionada = value ?? Categoria.personal;
              }),
            ),
            const SizedBox(height: 20),
            Center(
              child: ElevatedButton.icon(
                onPressed: _guardarTarea,
                label: Text(
                  widget.tareaParaEditar != null
                      ? 'Guardar cambios'
                      : 'Agregar tarea',
                ),
                icon: Icon(
                  widget.tareaParaEditar != null ? Icons.save : Icons.add_task,
                ),
                style: ElevatedButton.styleFrom(
                  backgroundColor: Theme.of(context).colorScheme.secondary,
                  padding: const EdgeInsets.symmetric(
                    horizontal: 24,
                    vertical: 12,
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
