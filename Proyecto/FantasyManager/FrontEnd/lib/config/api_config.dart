class ApiConfig {
  // Cambiar según tu entorno
  static const String baseUrl = 'http://localhost:3000';
  static const String apiBaseUrl = '$baseUrl/api';
  
  // Endpoints
  static const String examplesEndpoint = '/examples';
  
  // Timeouts
  static const Duration connectTimeout = Duration(seconds: 30);
  static const Duration receiveTimeout = Duration(seconds: 30);
}
