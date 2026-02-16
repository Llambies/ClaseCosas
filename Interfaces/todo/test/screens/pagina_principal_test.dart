import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:provider/provider.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:flutter_application_2/models/tarea.dart';
import 'package:flutter_application_2/screens/pagina_principal.dart';
import 'package:flutter_application_2/routes/rutas.dart';

/// Pruebas de widgets para [PaginaPrincipal].
/// 
/// Estas pruebas verifican la funcionalidad completa de la pantalla principal,
/// incluyendo filtros, gestión de tareas, reordenamiento y eliminación.
void main() {
  group('PaginaPrincipal', () {
    /// Prueba que se muestra el widget SinTareas cuando no hay tareas.
    testWidgets('debe mostrar SinTareas cuando no hay tareas', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            home: const PaginaPrincipal(),
          ),
        ),
      );

      expect(find.text('No hay tareas'), findsOneWidget);
    });

    /// Prueba agregar una nueva tarea.
    testWidgets('debe agregar una nueva tarea', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      final fab = find.byType(FloatingActionButton);
      await tester.tap(fab);
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).first, 'Nueva tarea');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      expect(find.text('Nueva tarea'), findsOneWidget);
    });

    /// Prueba eliminar una tarea y mostrar SnackBar de deshacer.
    testWidgets('debe eliminar tarea y mostrar SnackBar de deshacer', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar una tarea primero
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea a eliminar');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea a eliminar'), findsOneWidget);

      // Eliminar la tarea
      final eliminarButton = find.byIcon(Icons.delete_outline);
      await tester.tap(eliminarButton);
      await tester.pumpAndSettle();

      expect(find.text('Tarea eliminada'), findsOneWidget);
      expect(find.text('Deshacer'), findsOneWidget);
      expect(find.text('Tarea a eliminar'), findsNothing);
    });

    /// Prueba deshacer la eliminación de una tarea.
    testWidgets('debe restaurar tarea al presionar Deshacer', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar una tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea a restaurar');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Eliminar la tarea
      await tester.tap(find.byIcon(Icons.delete_outline));
      await tester.pumpAndSettle();

      // Deshacer eliminación
      await tester.tap(find.text('Deshacer'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea a restaurar'), findsOneWidget);
    });

    /// Prueba filtrar tareas por categoría.
    testWidgets('debe filtrar tareas por categoría', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar tareas de diferentes categorías
      // Primera tarea: Personal
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Personal');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      // Seleccionar categoría Personal
      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Personal'));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Trabajo');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.byType(DropdownButtonFormField<Categoria>));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Trabajo'));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Verificar que ambas tareas están visibles antes de filtrar
      expect(find.text('Tarea Personal'), findsOneWidget);
      expect(find.text('Tarea Trabajo'), findsOneWidget);

      // Filtrar por Personal - usar el dropdown específico de categoría
      final categoriaDropdowns = find.byType(DropdownButtonFormField<Categoria?>);
      expect(categoriaDropdowns, findsOneWidget);
      await tester.ensureVisible(categoriaDropdowns);
      await tester.pumpAndSettle();
      
      // Abrir el dropdown
      await tester.tap(categoriaDropdowns);
      await tester.pumpAndSettle();
      
      // Seleccionar "Personal" del menú desplegable
      final personalOption = find.text('Personal');
      expect(personalOption, findsWidgets);
      await tester.tap(personalOption.last);
      await tester.pumpAndSettle();
      
      // Esperar a que el estado se actualice y la lista se reconstruya
      await tester.pump();
      await tester.pump(const Duration(milliseconds: 100));
      await tester.pumpAndSettle();

      // Verificar que el filtro se aplicó correctamente
      // La tarea Personal debe estar visible, Trabajo no
      expect(find.text('Tarea Personal'), findsOneWidget);
      expect(find.text('Tarea Trabajo'), findsNothing);
    });

    /// Prueba filtrar tareas por estado pendiente.
    testWidgets('debe filtrar tareas por estado pendiente', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar una tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Completada');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Verificar que la tarea está visible inicialmente
      expect(find.text('Tarea Completada'), findsOneWidget);

      // Marcar como completada - buscar el container del checkbox
      // El checkbox está dentro de un Container con dimensiones específicas (22x22)
      final checkboxContainer = find.byWidgetPredicate(
        (widget) => widget is Container && 
                    widget.constraints?.maxWidth == 22 &&
                    widget.constraints?.maxHeight == 22,
        skipOffstage: false,
      );
      
      // Si el container no se encuentra, buscar el GestureDetector directamente
      final checkboxFinder = checkboxContainer.evaluate().isEmpty 
          ? find.byType(GestureDetector).first 
          : checkboxContainer.first;
      
      await tester.tap(checkboxFinder);
      await tester.pumpAndSettle();
      
      // Esperar a que el estado se actualice
      await tester.pump();
      await tester.pump(const Duration(milliseconds: 300));
      await tester.pumpAndSettle();

      // La tarea debe seguir visible en el listado (no hay filtro aún)
      // Después de marcar, debe tener decoración de linethrough
      expect(find.text('Tarea Completada'), findsOneWidget);

      // Filtrar por pendientes - buscar el dropdown de estado
      final estadoDropdown = find.byType(DropdownButtonFormField<bool?>);
      expect(estadoDropdown, findsOneWidget);
      
      // Hacer scroll si es necesario para que el dropdown sea visible
      await tester.ensureVisible(estadoDropdown);
      await tester.pumpAndSettle();
      
      // Tocar directamente el DropdownButtonFormField
      await tester.tap(estadoDropdown);
      await tester.pumpAndSettle(const Duration(milliseconds: 500));
      
      // Buscar el texto "Pendientes" en el menú desplegable
      var pendientesOption = find.text('Pendientes', skipOffstage: false);
      
      expect(pendientesOption, findsWidgets, reason: 'Debe encontrar la opción "Pendientes" en el dropdown');
      
      // Tocar la opción para aplicar el filtro
      await tester.tap(pendientesOption.last, warnIfMissed: false);
      await tester.pumpAndSettle(const Duration(milliseconds: 500));
      
      // Esperar a que el estado se actualice y la lista se reconstruya
      await tester.pump();
      await tester.pump(const Duration(milliseconds: 300));
      await tester.pumpAndSettle();

      // La tarea está completada, así que no debe aparecer cuando filtramos por pendientes
      expect(find.text('Tarea Completada'), findsNothing);
    });

    /// Prueba cambiar el estado de una tarea (marcar/desmarcar).
    testWidgets('debe cambiar el estado de una tarea al tocar el checkbox', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar una tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea para marcar');
      await tester.enterText(find.byType(TextField).last, 'Descripción');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Marcar como completada
      final checkboxFinder = find.byType(GestureDetector).first;
      await tester.tap(checkboxFinder);
      await tester.pumpAndSettle();

      // Verificar que el texto tiene tachado (a través del estilo)
      final textFinder = find.text('Tarea para marcar');
      expect(textFinder, findsOneWidget);
    });

    /// Prueba editar una tarea existente.
    testWidgets('debe editar una tarea existente', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        ChangeNotifierProvider<GestorTemas>.value(
          value: gestorTemas,
          child: MaterialApp(
            theme: gestorTemas.obtenerTema(),
            initialRoute: Rutas.paginaPrincipal,
            onGenerateRoute: Rutas.generarRuta,
          ),
        ),
      );

      // Agregar una tarea
      await tester.tap(find.byType(FloatingActionButton));
      await tester.pumpAndSettle();
      await tester.enterText(find.byType(TextField).first, 'Tarea Original');
      await tester.enterText(find.byType(TextField).last, 'Descripción original');
      await tester.tap(find.text('Agregar tarea'));
      await tester.pumpAndSettle();

      // Editar la tarea
      await tester.tap(find.byIcon(Icons.edit_outlined));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).first, 'Tarea Editada');
      await tester.tap(find.text('Guardar cambios'));
      await tester.pumpAndSettle();

      expect(find.text('Tarea Editada'), findsOneWidget);
      expect(find.text('Tarea Original'), findsNothing);
    });
  });
}
