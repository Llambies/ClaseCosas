class Validador {
  static bool validarEmail(String email) {
    return email.isNotEmpty;
  }

  static bool validarPassword(String password) {
    return password.isNotEmpty;
  }
}