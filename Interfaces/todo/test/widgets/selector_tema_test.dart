import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:provider/provider.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';
import 'package:flutter_application_2/widgets/selector_tema.dart';

/// Pruebas de widgets para [SelectorTema].
/// 
/// Estas pruebas verifican la funcionalidad del selector de temas,
/// incluyendo la apertura del menú y la selección de modos e intensidades.
void main() {
  group('SelectorTema', () {
    /// Prueba que el widget se renderiza correctamente.
    testWidgets('debe renderizar el botón del selector', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      expect(find.byIcon(Icons.palette), findsOneWidget);
    });

    /// Prueba que el menú se abre al presionar el botón.
    testWidgets('debe abrir el menú al presionar el botón', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      expect(find.text('Modo'), findsOneWidget);
      expect(find.text('Intensidad'), findsOneWidget);
    });

    /// Prueba la selección del modo claro.
    testWidgets('debe cambiar a modo claro al seleccionarlo', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Claro'));
      await tester.pumpAndSettle();

      expect(gestorTemas.modoTema, ModoTema.claro);
      expect(gestorTemas.esModoOscuro, false);
    });

    /// Prueba la selección del modo oscuro.
    testWidgets('debe cambiar a modo oscuro al seleccionarlo', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Oscuro'));
      await tester.pumpAndSettle();

      expect(gestorTemas.modoTema, ModoTema.oscuro);
      expect(gestorTemas.esModoOscuro, true);
    });

    /// Prueba la selección del modo sistema.
    testWidgets('debe cambiar a modo sistema al seleccionarlo', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();
      gestorTemas.cambiarModoTema(ModoTema.claro);

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Sistema'));
      await tester.pumpAndSettle();

      expect(gestorTemas.modoTema, ModoTema.sistema);
    });

    /// Prueba la selección de intensidad soft.
    testWidgets('debe cambiar a intensidad soft al seleccionarla', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Soft'));
      await tester.pumpAndSettle();

      expect(gestorTemas.intensidad, IntensidadGruvbox.soft);
    });

    /// Prueba la selección de intensidad normal.
    testWidgets('debe cambiar a intensidad normal al seleccionarla', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();
      gestorTemas.cambiarIntensidad(IntensidadGruvbox.soft);

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Normal'));
      await tester.pumpAndSettle();

      expect(gestorTemas.intensidad, IntensidadGruvbox.normal);
    });

    /// Prueba la selección de intensidad hard.
    testWidgets('debe cambiar a intensidad hard al seleccionarla', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      await tester.tap(find.text('Hard'));
      await tester.pumpAndSettle();

      expect(gestorTemas.intensidad, IntensidadGruvbox.hard);
    });

    /// Prueba que se muestra el indicador de selección para el modo actual.
    testWidgets('debe mostrar indicador de selección para el modo actual', (WidgetTester tester) async {
      final gestorTemas = GestorTemas();
      gestorTemas.cambiarModoTema(ModoTema.oscuro);

      await tester.pumpWidget(
        MaterialApp(
          home: ChangeNotifierProvider<GestorTemas>.value(
            value: gestorTemas,
            child: Scaffold(
              appBar: AppBar(
                actions: const [SelectorTema()],
              ),
            ),
          ),
        ),
      );

      await tester.tap(find.byIcon(Icons.palette));
      await tester.pumpAndSettle();

      expect(find.byIcon(Icons.check), findsWidgets);
    });
  });
}
