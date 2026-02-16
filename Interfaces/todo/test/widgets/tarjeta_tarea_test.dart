import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_application_2/models/tarea.dart';
import 'package:flutter_application_2/widgets/tarjeta_tarea.dart';

/// Pruebas de widgets para [TarjetaTarea].
/// 
/// Estas pruebas verifican el renderizado correcto de la tarjeta,
/// los callbacks de interacción y la visualización de diferentes estados.
void main() {
  group('TarjetaTarea', () {
    /// Prueba el renderizado de una tarea pendiente.
    testWidgets('debe renderizar una tarea pendiente correctamente', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea pendiente',
        descripcion: 'Descripción de prueba',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      var estadoCambiado = false;
      var eliminada = false;
      var editada = false;

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () => estadoCambiado = true,
              onEliminar: () => eliminada = true,
              onEditar: () => editada = true,
            ),
          ),
        ),
      );

      expect(find.text('Tarea pendiente'), findsOneWidget);
      expect(find.text('Descripción de prueba'), findsOneWidget);
      expect(find.text('Personal'), findsOneWidget);
    });

    /// Prueba el renderizado de una tarea completada.
    testWidgets('debe renderizar una tarea completada correctamente', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea completada',
        descripcion: 'Descripción',
        estaTerminada: true,
        categoria: Categoria.trabajo,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () {},
              onEliminar: () {},
              onEditar: () {},
            ),
          ),
        ),
      );

      expect(find.text('Tarea completada'), findsOneWidget);
      expect(find.text('Trabajo'), findsOneWidget);
    });

    /// Prueba que el callback onCambiarEstado se ejecuta al tocar el checkbox.
    testWidgets('debe ejecutar onCambiarEstado al tocar el checkbox', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.otro,
      );

      var estadoCambiado = false;

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () => estadoCambiado = true,
              onEliminar: () {},
              onEditar: () {},
            ),
          ),
        ),
      );

      final checkboxFinder = find.byType(GestureDetector).first;
      await tester.tap(checkboxFinder);
      await tester.pumpAndSettle();

      expect(estadoCambiado, true);
    });

    /// Prueba que el callback onEliminar se ejecuta al presionar el botón eliminar.
    testWidgets('debe ejecutar onEliminar al presionar el botón eliminar', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      var eliminada = false;

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () {},
              onEliminar: () => eliminada = true,
              onEditar: () {},
            ),
          ),
        ),
      );

      final eliminarButton = find.byIcon(Icons.delete_outline);
      expect(eliminarButton, findsOneWidget);

      await tester.tap(eliminarButton);
      await tester.pumpAndSettle();

      expect(eliminada, true);
    });

    /// Prueba que el callback onEditar se ejecuta al presionar el botón editar.
    testWidgets('debe ejecutar onEditar al presionar el botón editar', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      var editada = false;

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () {},
              onEliminar: () {},
              onEditar: () => editada = true,
            ),
          ),
        ),
      );

      final editarButton = find.byIcon(Icons.edit_outlined);
      expect(editarButton, findsOneWidget);

      await tester.tap(editarButton);
      await tester.pumpAndSettle();

      expect(editada, true);
    });

    /// Prueba que el botón editar no se muestra cuando la tarea está completada.
    testWidgets('no debe mostrar botón editar cuando la tarea está completada', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea completada',
        descripcion: 'Descripción',
        estaTerminada: true,
        categoria: Categoria.personal,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () {},
              onEliminar: () {},
              onEditar: () {},
            ),
          ),
        ),
      );

      final editarButton = find.byIcon(Icons.edit_outlined);
      expect(editarButton, findsNothing);
    });

    /// Prueba que se muestra el handle de reordenamiento cuando se proporciona índice.
    testWidgets('debe mostrar handle de reordenamiento cuando se proporciona índice', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              indiceReordenar: 0,
              onCambiarEstado: () {},
              onEliminar: () {},
              onEditar: () {},
            ),
          ),
        ),
      );

      expect(find.byIcon(Icons.drag_handle), findsOneWidget);
    });

    /// Prueba que no se muestra la descripción cuando está vacía.
    testWidgets('no debe mostrar descripción cuando está vacía', (WidgetTester tester) async {
      final tarea = Tarea(
        titulo: 'Tarea sin descripción',
        descripcion: '',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: TarjetaTarea(
              tarea: tarea,
              onCambiarEstado: () {},
              onEliminar: () {},
              onEditar: () {},
            ),
          ),
        ),
      );

      expect(find.text('Tarea sin descripción'), findsOneWidget);
      expect(find.text('Descripción'), findsNothing);
    });
  });
}
