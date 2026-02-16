import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_application_2/core/gestor_temas.dart';

/// Pruebas unitarias para [GestorTemas].
/// 
/// Estas pruebas verifican la gestión de temas, modos (claro/oscuro/sistema),
/// intensidades Gruvbox y la notificación de cambios.
void main() {
  // Inicializar el binding de Flutter para que google_fonts funcione en los tests
  TestWidgetsFlutterBinding.ensureInitialized();

  group('GestorTemas', () {
    late GestorTemas gestorTemas;

    /// Configuración inicial antes de cada prueba.
    setUp(() {
      gestorTemas = GestorTemas();
    });

    /// Prueba el valor inicial del modo de tema.
    test('debe inicializar con modo sistema por defecto', () {
      expect(gestorTemas.modoTema, ModoTema.sistema);
    });

    /// Prueba el cambio de modo de tema a claro.
    test('debe cambiar a modo claro', () {
      gestorTemas.cambiarModoTema(ModoTema.claro);
      expect(gestorTemas.modoTema, ModoTema.claro);
      expect(gestorTemas.esModoOscuro, false);
    });

    /// Prueba el cambio de modo de tema a oscuro.
    test('debe cambiar a modo oscuro', () {
      gestorTemas.cambiarModoTema(ModoTema.oscuro);
      expect(gestorTemas.modoTema, ModoTema.oscuro);
      expect(gestorTemas.esModoOscuro, true);
    });

    /// Prueba el cambio de modo de tema a sistema.
    test('debe cambiar a modo sistema', () {
      gestorTemas.cambiarModoTema(ModoTema.sistema);
      expect(gestorTemas.modoTema, ModoTema.sistema);
    });

    /// Prueba el valor inicial de la intensidad Gruvbox.
    test('debe inicializar con intensidad normal por defecto', () {
      expect(gestorTemas.intensidad, IntensidadGruvbox.normal);
    });

    /// Prueba el cambio de intensidad a soft.
    test('debe cambiar intensidad a soft', () {
      gestorTemas.cambiarIntensidad(IntensidadGruvbox.soft);
      expect(gestorTemas.intensidad, IntensidadGruvbox.soft);
    });

    /// Prueba el cambio de intensidad a normal.
    test('debe cambiar intensidad a normal', () {
      gestorTemas.cambiarIntensidad(IntensidadGruvbox.normal);
      expect(gestorTemas.intensidad, IntensidadGruvbox.normal);
    });

    /// Prueba el cambio de intensidad a hard.
    test('debe cambiar intensidad a hard', () {
      gestorTemas.cambiarIntensidad(IntensidadGruvbox.hard);
      expect(gestorTemas.intensidad, IntensidadGruvbox.hard);
    });

    /// Prueba la actualización del modo oscuro del sistema.
    test('debe actualizar modo oscuro del sistema cuando está en modo sistema', () {
      gestorTemas.cambiarModoTema(ModoTema.sistema);
      gestorTemas.actualizarModoOscuroSistema(true);
      expect(gestorTemas.esModoOscuro, true);

      gestorTemas.actualizarModoOscuroSistema(false);
      expect(gestorTemas.esModoOscuro, false);
    });

    /// Prueba que no se actualiza el modo oscuro cuando no está en modo sistema.
    test('no debe actualizar modo oscuro cuando no está en modo sistema', () {
      gestorTemas.cambiarModoTema(ModoTema.claro);
      gestorTemas.actualizarModoOscuroSistema(true);
      expect(gestorTemas.esModoOscuro, false);

      gestorTemas.cambiarModoTema(ModoTema.oscuro);
      gestorTemas.actualizarModoOscuroSistema(false);
      expect(gestorTemas.esModoOscuro, true);
    });

    /// Prueba la generación de tema en modo claro.
    testWidgets('debe generar tema claro correctamente', (WidgetTester tester) async {
      gestorTemas.cambiarModoTema(ModoTema.claro);
      final tema = gestorTemas.obtenerTema();

      expect(tema.brightness, Brightness.light);
      expect(tema.scaffoldBackgroundColor, isNotNull);
      
      // Esperar a que las operaciones asíncronas de google_fonts terminen
      await tester.pumpAndSettle();
    });

    /// Prueba la generación de tema en modo oscuro.
    testWidgets('debe generar tema oscuro correctamente', (WidgetTester tester) async {
      gestorTemas.cambiarModoTema(ModoTema.oscuro);
      final tema = gestorTemas.obtenerTema();

      expect(tema.brightness, Brightness.dark);
      expect(tema.scaffoldBackgroundColor, isNotNull);
      
      // Esperar a que las operaciones asíncronas de google_fonts terminen
      await tester.pumpAndSettle();
    });

    /// Prueba la generación de temas con diferentes intensidades en modo claro.
    testWidgets('debe generar temas con diferentes intensidades en modo claro', (WidgetTester tester) async {
      gestorTemas.cambiarModoTema(ModoTema.claro);

      gestorTemas.cambiarIntensidad(IntensidadGruvbox.soft);
      final temaSoft = gestorTemas.obtenerTema();

      gestorTemas.cambiarIntensidad(IntensidadGruvbox.hard);
      final temaHard = gestorTemas.obtenerTema();

      expect(temaSoft.scaffoldBackgroundColor, isNot(equals(temaHard.scaffoldBackgroundColor)));
      
      // Esperar a que las operaciones asíncronas de google_fonts terminen
      await tester.pumpAndSettle();
    });

    /// Prueba la generación de temas con diferentes intensidades en modo oscuro.
    testWidgets('debe generar temas con diferentes intensidades en modo oscuro', (WidgetTester tester) async {
      gestorTemas.cambiarModoTema(ModoTema.oscuro);

      gestorTemas.cambiarIntensidad(IntensidadGruvbox.soft);
      final temaSoft = gestorTemas.obtenerTema();

      gestorTemas.cambiarIntensidad(IntensidadGruvbox.hard);
      final temaHard = gestorTemas.obtenerTema();

      expect(temaSoft.scaffoldBackgroundColor, isNot(equals(temaHard.scaffoldBackgroundColor)));
      
      // Esperar a que las operaciones asíncronas de google_fonts terminen
      await tester.pumpAndSettle();
    });

    /// Prueba que el gestor notifica cambios cuando se modifica el modo.
    test('debe notificar cambios cuando se cambia el modo', () {
      var notificado = false;
      gestorTemas.addListener(() {
        notificado = true;
      });

      gestorTemas.cambiarModoTema(ModoTema.oscuro);
      expect(notificado, true);
    });

    /// Prueba que el gestor notifica cambios cuando se modifica la intensidad.
    test('debe notificar cambios cuando se cambia la intensidad', () {
      var notificado = false;
      gestorTemas.addListener(() {
        notificado = true;
      });

      gestorTemas.cambiarIntensidad(IntensidadGruvbox.hard);
      expect(notificado, true);
    });

    /// Prueba que el gestor notifica cambios cuando se actualiza el modo del sistema.
    test('debe notificar cambios cuando se actualiza el modo del sistema', () {
      gestorTemas.cambiarModoTema(ModoTema.sistema);
      var notificado = false;
      gestorTemas.addListener(() {
        notificado = true;
      });

      gestorTemas.actualizarModoOscuroSistema(true);
      expect(notificado, true);
    });
  });
}
