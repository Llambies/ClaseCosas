import 'package:flutter_test/flutter_test.dart';
import 'package:tests/features/login_view_model.dart';
import 'package:mocktail/mocktail.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MockSharedPreferences extends Mock implements SharedPreferences {

}

void main() {
  group('Login ~> (LoginViewModel.login)', () {
    test('login exitoso ~> (true)', () {
      // Arrange
      final String email = 'test@test.com';
      final String password = 'password';
      final clase = LoginViewModel(sharedPreferences: MockSharedPreferences());
      // Act
      final bool resultado = clase.login(email, password);
      // Assert
      expect(resultado, true);
    });
  });
}
