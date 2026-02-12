import 'package:flutter_test/flutter_test.dart';
import 'package:tests/utils/validador.dart';

void main() {
  group('Validar correos ~> (validarEmail)', () {
    test('correo valido ~> (true)', () {
      // Arrange
      final String emailValido = 'test@test.com';

      // Act
      final bool resultado = Validador.validarEmail(emailValido);

      // Assert
      expect(resultado, true);
    });
    test('correo vacio ~> (false)', () {
      // Arrange
      final String emailInvalido = '';

      // Act
      final bool resultado = Validador.validarEmail(emailInvalido);

      // Assert
      expect(resultado, false);
    });
  });

  group('Validar contraseñas ~> (validarPassword)', () {
    test('contraseña valida ~> (true)', () {
      // Arrange
      final String passwordValido = 'password';

      // Act
      final bool resultado = Validador.validarPassword(passwordValido);

      // Assert
      expect(resultado, true);
    });
    test('contraseña vacia ~> (false)', () {
      // Arrange
      final String passwordInvalido = '';

      // Act
      final bool resultado = Validador.validarPassword(passwordInvalido);

      // Assert
      expect(resultado, false);
    });
  });
}
