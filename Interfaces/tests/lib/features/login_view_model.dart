import 'package:flutter/material.dart';
import 'package:tests/utils/extensiones.dart';
import 'package:tests/utils/validador.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginViewModel {
  final SharedPreferences sharedPreferences;

  LoginViewModel({required this.sharedPreferences});

  bool login(String? email, String? password) {
    final passwordAlmacenada = sharedPreferences.getString('password');

    final emailAlmacenado = sharedPreferences.getString('email');

    return passwordAlmacenada == password &&
        emailAlmacenado == email; // Para probar los de las preferences

    if (email.isNullOrEmpty || password.isNullOrEmpty) {
      return false;
    }
    return Validador.validarEmail(email!) &&
        Validador.validarPassword(password!);
  }

  Future<bool> logout() async {
    bool ok = false;

    try {
      ok = await sharedPreferences.clear();
    } catch (e) {
      ok = false;
    }

    if (!ok) {
      throw FlutterError("Error al cerrar sesión");
    }

    return ok;
  }
}
