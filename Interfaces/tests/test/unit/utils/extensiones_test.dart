import 'package:flutter_test/flutter_test.dart';
import 'package:tests/utils/extensiones.dart';

void main() {
  group('Extensiones de strings ~> (isNullOrEmpty)', () {
    test('cadena no vacia ni nula ~> (false)', () {
      // Arrange
      final String cadenaLlena = 'test';

      // Act
      final bool resultado = cadenaLlena.isNullOrEmpty;

      // Assert
      expect(resultado, false);
    });
    test('cadena vacia ~> (true)', () {
      // Arrange
      final String cadenaVacia = '';

      // Act
      final bool resultado = cadenaVacia.isNullOrEmpty;

      // Assert
      expect(resultado, true);
    });
    test('cadena nula ~> (true)', () {
      // Arrange
      final String? cadenaNula = null;

      // Act
      final bool resultado = cadenaNula.isNullOrEmpty;

      // Assert
      expect(resultado, true);
    });
  });
}