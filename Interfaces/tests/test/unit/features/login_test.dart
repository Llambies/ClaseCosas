import 'package:flutter_test/flutter_test.dart';
import 'package:tests/features/login.dart';

void main() {
  group('Login ~> (login)', () {
    test('login exitoso ~> (true)', () {
      // Arrange
      final String email = 'test@test.com';
      final String password = 'password';

      // Act
      final bool resultado = login(email, password);

      // Assert
      expect(resultado, true);
    });
    test('login fallido por contraseña vacia ~> (false)', () {
      // Arrange
      final String email = 'test@test.com';
      final String password = '';

      // Act
      final bool resultado = login(email, password);

      // Assert
      expect(resultado, false);
    });
    test('login fallido por correo vacio ~> (false)', () {
      // Arrange
      final String email = '';
      final String password = '1234';
      // Act
      final bool resultado = login(email, password);

      // Assert
      expect(resultado, false);
    });
    test('login fallido por correo y contraseña vacios  ~> (false)', () {
      // Arrange
      final String email = '';
      final String password = '';
      // Act
      final bool resultado = login(email, password);
      // Assert
      expect(resultado, false);
    });
    test('login fallido por correo nulo ~> (false)', () {
      // Arrange
      final String? email = null;
      final String password = '1234';
      // Act
      final bool resultado = login(email, password);
      // Assert
      expect(resultado, false);
    });
    test('login fallido por contraseña nula ~> (false)', () {
      // Arrange
      final String email = 'test@test.com';
      final String? password = null;
      // Act
      final bool resultado = login(email, password);
      // Assert
      expect(resultado, false);
    });
    test('login fallido por correo y contraseña nulos ~> (false)', () {
      // Arrange
      final String? email = null;
      final String? password = null;
      // Act
      final bool resultado = login(email, password);
      // Assert
      expect(resultado, false);
    });
  });
}
