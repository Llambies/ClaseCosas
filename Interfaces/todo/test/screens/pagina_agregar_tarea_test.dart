import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_application_2/models/tarea.dart';
import 'package:flutter_application_2/screens/pagina_agregar_tarea.dart';

/// Pruebas de widgets para [PaginaAgregarTarea].
/// 
/// Estas pruebas verifican la creación y edición de tareas,
/// validación de campos y selección de categorías.
void main() {
  group('PaginaAgregarTarea', () {
    /// Prueba la creación de una nueva tarea.
    testWidgets('debe crear una nueva tarea con todos los campos', (WidgetTester tester) async {
      Tarea? tareaCreada;

      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              return Scaffold(
                body: PaginaAgregarTarea(),
              );
            },
          ),
          onGenerateRoute: (settings) {
            if (settings.name == '/') {
              return MaterialPageRoute(
                builder: (_) => Scaffold(
                  body: Builder(
                    builder: (context) {
                      Navigator.of(context).pop(tareaCreada);
                      return Container();
                    },
                  ),
                ),
              );
            }
            return null;
          },
        ),
      );

      await tester.enterText(find.byType(TextField).first, 'Nueva Tarea');
      await tester.enterText(find.byType(TextField).last, 'Descripción de la tarea');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Verificar que se navegó de vuelta (simulado)
      expect(find.text('Nueva Tarea'), findsNothing);
    });

    /// Prueba la edición de una tarea existente.
    testWidgets('debe cargar datos de tarea existente al editar', (WidgetTester tester) async {
      final tareaExistente = Tarea(
        id: 'id-123',
        titulo: 'Tarea Original',
        descripcion: 'Descripción original',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(tareaParaEditar: tareaExistente),
        ),
      );

      expect(find.text('Tarea Original'), findsOneWidget);
      expect(find.text('Descripción original'), findsOneWidget);
      expect(find.text('Guardar cambios'), findsOneWidget);
    });

    /// Prueba la validación de título obligatorio.
    testWidgets('debe mostrar mensaje de error si el título está vacío', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      // Intentar guardar sin título
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      expect(find.text('El título es obligatorio'), findsOneWidget);
    });

    /// Prueba la selección de categoría Personal.
    testWidgets('debe permitir seleccionar categoría Personal', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Personal'));
      await tester.pumpAndSettle();

      expect(find.text('Personal'), findsWidgets);
    });

    /// Prueba la selección de categoría Trabajo.
    testWidgets('debe permitir seleccionar categoría Trabajo', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Trabajo'));
      await tester.pumpAndSettle();

      expect(find.text('Trabajo'), findsWidgets);
    });

    /// Prueba la selección de categoría Otro.
    testWidgets('debe permitir seleccionar categoría Otro', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();

      // Usar .last para seleccionar el texto del menú desplegable, no el del dropdown cerrado
      await tester.tap(find.text('Otro').last);
      await tester.pumpAndSettle();

      expect(find.text('Otro'), findsWidgets);
    });

    /// Prueba que se puede guardar una tarea con solo título.
    testWidgets('debe permitir guardar tarea con solo título', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      await tester.enterText(find.byType(TextField).first, 'Tarea sin descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // No debe mostrar error
      expect(find.text('El título es obligatorio'), findsNothing);
    });

    /// Prueba que el botón cambia de texto al editar.
    testWidgets('debe mostrar "Guardar cambios" cuando se edita una tarea', (WidgetTester tester) async {
      final tareaExistente = Tarea(
        id: 'id-123',
        titulo: 'Tarea',
        descripcion: 'Descripción',
        estaTerminada: false,
        categoria: Categoria.personal,
      );

      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(tareaParaEditar: tareaExistente),
        ),
      );

      expect(find.text('Guardar cambios'), findsOneWidget);
      expect(find.text('Agregar tarea'), findsNothing);
    });

    /// Prueba que el botón muestra "Agregar tarea" cuando se crea nueva.
    testWidgets('debe mostrar "Agregar tarea" cuando se crea nueva tarea', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: PaginaAgregarTarea(),
        ),
      );

      expect(find.text('Agregar tarea'), findsOneWidget);
      expect(find.text('Guardar cambios'), findsNothing);
    });
  });
}
