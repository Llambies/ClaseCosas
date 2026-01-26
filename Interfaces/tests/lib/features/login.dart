import 'package:tests/utils/extensiones.dart';
import 'package:tests/utils/validador.dart';

bool login(String? email, String? password) {
  if (email.isNullOrEmpty || password.isNullOrEmpty) {
    return false;
  }
  return Validador.validarEmail(email!) && Validador.validarPassword(password!);
}
