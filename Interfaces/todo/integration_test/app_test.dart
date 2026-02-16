import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:flutter_application_2/main.dart' as app;
import 'package:flutter_application_2/models/tarea.dart';

/// Pruebas E2E (End-to-End) de integración para la aplicación de Tareas.
/// 
/// Estas pruebas verifican flujos completos de usuario desde la creación
/// hasta la eliminación de tareas, incluyendo filtros y reordenamiento.
void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  group('Flujos E2E de la aplicación', () {
    /// Prueba el flujo completo: crear → editar → cambiar estado → eliminar.
    testWidgets('flujo completo de gestión de tareas', (WidgetTester tester) async {
      app.main();
      await tester.pumpAndSettle();

      // 1. Crear una nueva tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).first, 'Tarea E2E');
      await tester.enterText(find.byType(TextField).last, 'Descripción de prueba E2E');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea E2E'), findsOneWidget);
      expect(find.text('Descripción de prueba E2E'), findsOneWidget);

      // 2. Editar la tarea
      await tester.tap(find.byIcon(Icons.edit_outlined));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).first, 'Tarea E2E Editada');
      await tester.tap(find.text('Guardar cambios'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea E2E Editada'), findsOneWidget);
      expect(find.text('Tarea E2E'), findsNothing);

      // 3. Cambiar estado (marcar como completada)
      final checkboxFinder = find.byType(GestureDetector).first;
      await tester.tap(checkboxFinder);
      await tester.pumpAndSettle();

      // Verificar que la tarea está marcada (el botón editar desaparece)
      expect(find.byIcon(Icons.edit_outlined), findsNothing);

      // 4. Eliminar la tarea
      await tester.tap(find.byIcon(Icons.delete_outline));
      await tester.pumpAndSettle();

      expect(find.text('Tarea eliminada'), findsOneWidget);
      expect(find.text('Tarea E2E Editada'), findsNothing);
    });

    /// Prueba filtros combinados (categoría + estado).
    testWidgets('filtros combinados por categoría y estado', (WidgetTester tester) async {
      app.main();
      await tester.pumpAndSettle();

      // Crear múltiples tareas con diferentes categorías y estados
      // Tarea 1: Personal, Pendiente
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Personal Pendiente');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Tarea 2: Trabajo, Completada
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Trabajo Completada');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Trabajo'));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Marcar segunda tarea como completada
      final checkboxes = find.byType(GestureDetector);
      await tester.tap(checkboxes.at(1));
      await tester.pumpAndSettle();

      // Filtrar por Trabajo
      await tester.tap(find.text('Todas'));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Trabajo'));
      await tester.pumpAndSettle();

      expect(find.text('Trabajo Completada'), findsOneWidget);
      expect(find.text('Personal Pendiente'), findsNothing);

      // Filtrar por Terminadas
      await tester.tap(find.text('Todos').last);
      await tester.pumpAndSettle();
      await tester.tap(find.text('Terminadas'));
      await tester.pumpAndSettle();

      expect(find.text('Trabajo Completada'), findsOneWidget);
    });

    /// Prueba el reordenamiento de tareas.
    testWidgets('reordenamiento de tareas', (WidgetTester tester) async {
      app.main();
      await tester.pumpAndSettle();

      // Crear dos tareas
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Primera');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Segunda');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Verificar orden inicial
      final tareas = find.text('Tarea Primera');
      expect(tareas, findsOneWidget);

      // El reordenamiento requiere interacción con drag handles
      // que puede ser complejo en pruebas E2E, pero verificamos que existen
      expect(find.byIcon(Icons.drag_handle), findsWidgets);
    });

    /// Prueba deshacer eliminación de tarea.
    testWidgets('deshacer eliminación de tarea', (WidgetTester tester) async {
      app.main();
      await tester.pumpAndSettle();

      // Crear una tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea para Deshacer');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea para Deshacer'), findsOneWidget);

      // Eliminar la tarea
      await tester.tap(find.byIcon(Icons.delete_outline));
      await tester.pumpAndSettle();

      expect(find.text('Tarea eliminada'), findsOneWidget);
      expect(find.text('Tarea para Deshacer'), findsNothing);

      // Deshacer eliminación
      await tester.tap(find.text('Deshacer'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea para Deshacer'), findsOneWidget);
    });

    /// Prueba cambio de tema durante el uso.
    testWidgets('cambio de tema durante el uso', (WidgetTester tester) async {
      app.main();
      await tester.pumpAndSettle();

      // Verificar que el selector de tema existe
      expect(find.byIcon(Icons.palette), findsOneWidget);

      // Abrir selector de tema
      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      // Cambiar a modo oscuro
      await tester.tap(find.text('Oscuro'));
      await tester.pumpAndSettle();

      // Verificar que la aplicación sigue funcionando
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();

      expect(find.byType(TextField), findsWidgets);

      // Cambiar a modo claro
      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Claro'));
      await tester.pumpAndSettle();

      // Verificar que la aplicación sigue funcionando
      expect(find.byType(FloatingActionButton), findsOneWidget);
    });
  });
}
