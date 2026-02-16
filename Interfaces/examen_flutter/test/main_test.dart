import 'package:examen_flutter/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('WidgetFavorito', () {
    testWidgets('muestra estrella vacía y contador inicial 16', (tester) async {
      await tester.pumpWidget(
        const MaterialApp(
          home: Scaffold(
            body: WidgetFavorito(),
          ),
        ),
      );

      expect(find.byIcon(Icons.star_border), findsOneWidget);
      expect(find.text('16'), findsOneWidget);
    });

    testWidgets('al pulsar alterna a estrella rellena e incrementa contador',
        (tester) async {
      await tester.pumpWidget(
        const MaterialApp(
          home: Scaffold(
            body: WidgetFavorito(),
          ),
        ),
      );

      await tester.tap(find.byType(WidgetFavorito));
      await tester.pump();

      expect(find.byIcon(Icons.star), findsOneWidget);
      expect(find.text('17'), findsOneWidget);
    });

    testWidgets('al pulsar dos veces vuelve a estrella vacía y contador 16',
        (tester) async {
      await tester.pumpWidget(
        const MaterialApp(
          home: Scaffold(
            body: WidgetFavorito(),
          ),
        ),
      );

      await tester.tap(find.byType(WidgetFavorito));
      await tester.pump();
      await tester.tap(find.byType(WidgetFavorito));
      await tester.pump();

      expect(find.byIcon(Icons.star_border), findsOneWidget);
      expect(find.text('16'), findsOneWidget);
    });
  });
}
